package com.sutong.cache;

import com.sutong.bjstjh.entity.AuditDictionaryInfoTable;
import com.sutong.bjstjh.entity.DictEntity;
import com.sutong.bjstjh.exception.DataStaleException;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.service.IDictCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName:  DataCache
 * @Description:  用于字典数据缓存，若缓存中有该数据，则不向数据库中查询
 * 					暂定每天凌晨一点清理大于 MAX_CACHE 值的不常用字典 ??? 规则未定???
 * @author: pengwz
 * @date:   2019年12月20日 上午9:42:24
 */
@Component
@Scope("singleton")
public class DictCache implements java.io.Serializable{

	private static final long serialVersionUID = 2297148405081365733L;

	private static final Logger log = LoggerFactory.getLogger(DictCache.class);
	/**默认最大缓存字典类型条目*/
	private static final Integer MAX_CACHE = 100;
	/**字典缓存*/
	private static final ConcurrentHashMap<String,Map<String,Integer>> DICT_CACHE_MAP = new ConcurrentHashMap<>();
	/**计数器*/
	private static final ConcurrentHashMap<String,Integer> COUNTER = new ConcurrentHashMap<>();

	@Autowired
	@Qualifier("dictCacheService")
	private IDictCacheService dictCacheServiceAuto ;

	private static IDictCacheService dictCacheService ;

	@PostConstruct
	public void init() {
		if(dictCacheService == null) {
			dictCacheService = this.dictCacheServiceAuto;
			cleanTasks();
		}
	}

	/**
	 *
	 * @Description 往缓存中存放数据
	 * @param <T>
	 * @param dictType 字典表名，该表名指的是字典表中DICT_TYPE字段
	 * @param dictValue 字典值Map，key为字典名，value为字典值
	 * @return 缓存成功后，返回true
	 * @author pengwz
	 * @date 2019年12月20日 上午11:35:53
	 */
	public synchronized static <T extends Object> boolean put(Object dictType,Map<String,Integer> dictValue) {
		if(dictType == null || dictValue == null
				|| dictValue.isEmpty())
			return false;
		String dictTableUpper = String.valueOf(dictType).toUpperCase();
		//如果表名已经存在，则在之前的基础上继续添加
		if(DICT_CACHE_MAP.containsKey(dictTableUpper)) {
			Map<String, Integer> map = DICT_CACHE_MAP.get(dictTableUpper);
			Set<Entry<String,Integer>> entrySet = dictValue.entrySet();
			for (Entry<String, Integer> entry : entrySet) {
				if(entry.getValue() == null) {
					 return false;
				}
				map.put(entry.getKey(),entry.getValue());
			}
			DICT_CACHE_MAP.put(dictTableUpper, map);
			addCount(dictTableUpper);
		}else {
			Map<String,Integer> newMap = new HashMap<>();
			//深度复制对象
			newMap.putAll(dictValue);
			DICT_CACHE_MAP.put(dictTableUpper, newMap);
			addCount(dictTableUpper);
		}
		return true;
	}

	/**
	 * 根据字典类型返回字典数据
	 * @param dictType
	 * @return
	 * @author pengwz
	 * @date 2019年12月26日 下午11:24:49
	 */
	public static Map<String,Integer> query(Object dictType){
		if(dictType == null || dictType.toString().trim().equals("")) {
			String errMsg = "传入的字典类型不合法";
			log.error(errMsg);
			throw new DataStaleException(ResultCode.ERROR,errMsg);
		}
		Map<String, Integer> innerQuery = innerQuery(dictType);
		//如果缓存中没有,就去数据库中找,数据库中还没有,则提示并返回空集合
		String dictTypeUpper = String.valueOf(dictType).toUpperCase();
		if(innerQuery == null) {
			List<AuditDictionaryInfoTable> queryDB = queryDB(dictTypeUpper);
			if(queryDB != null) {
				DICT_CACHE_MAP.remove(dictTypeUpper);
				Map<String,Integer> map = new HashMap<String, Integer>();
				for (AuditDictionaryInfoTable table : queryDB) {
					map.put(table.getDictName(), table.getDictNumber());
				}
				DICT_CACHE_MAP.put(dictTypeUpper, map);
				//添加计数器
				addCount(dictTypeUpper);
				log.info("---------当前查询返回的字典类型：'"+dictType+"',数据来源：[数据库]"+"---------");
				//返回要查询的数据
				return map;
			}
			log.error("---------数据库中没有类型为：'"+dictType+"' 的字典---------");
			//没有则返回空集合
			return new HashMap<String, Integer>();
		}
		log.info("---------当前查询返回的字典类型：'"+dictType+"',数据来源：[系统缓存]"+"---------");
		addCount(dictTypeUpper);
		return innerQuery;
	}
	/***
	 * 	根据多个字典类型批量查询字典数据, 该方法会优先查询缓存中数据, 如果缓存中没有,
	 * 	就去数据库中查询, 如果仍然查询不到, 返回null .
	 * 	<p>
	 * 	<red>不想写了</red>
	 * @param dictType
	 * @return
	 * @author pengwz
	 * @date 2020年1月16日 上午9:34:51
	 */
	public static List<AuditDictionaryInfoTable> query(Object... dictType){
		Object[] obj = dictType;
		for (int i = 0; i < obj.length; i++) {
			if(obj[i] == null || !(obj[i] instanceof String) || obj[i].toString().trim().equals("")) {
				return null;
			}
		}
		return null;
	}
	/**
	 * 	根据传入的字典类型和字典名称匹配字典值
	 * @param dictType
	 * @param dictName
	 * @return 如果没有找到,返回null
	 * @author pengwz
	 * @date 2019年12月29日 下午5:09:03
	 */
	public static Integer queryValue(Object dictType,String dictName) {
		if(dictType == null || dictName == null ||dictType.toString().trim().equals(""))
			return null;
		//业务要求如果传入的字典值是斜杠或者空，一律返回null
		dictName = dictName.trim();
		if(dictName.equals("/") || dictName.equals("\\") ||dictName.trim().equals(""))
			return null;
		String type = dictType.toString().toUpperCase();
		Map<String, Integer> map = DICT_CACHE_MAP.get(type);
		//如果存在
		if(map != null) {
			Integer integer = map.get(dictName);
			if(integer != null) {
				addCount(type);
				return integer;
			}
			//有此类型但是没有字典
			Map<String, Integer> queryDictByType = queryDictByType(type);
			DICT_CACHE_MAP.remove(type);
			DICT_CACHE_MAP.put(type, queryDictByType);
			Integer integer2 = queryDictByType.get(dictName);
			if(integer2 == null)
				throw new DataStaleException(ResultCode.DATA_ERROR,"字典类型'"+type+"'下没有字典名为'"+dictName+"'的数据");
			addCount(type);
			return integer2;
		} else {
			//缓存不存在
			Map<String, Integer> queryDictByType = queryDictByType(type);
			DICT_CACHE_MAP.remove(type);
			DICT_CACHE_MAP.put(type, queryDictByType);
			Integer integer2 = queryDictByType.get(dictName);
			if(integer2 == null)
				throw new DataStaleException(ResultCode.DATA_ERROR,"字典类型'"+type+"'下没有字典名为'"+dictName+"'的数据");
			addCount(type);
			return integer2;
		}
	}

	/**
	 *
	 * @Description 根据字典类型和字典值查询字典名，该方法会优先查询缓存中数据，如果找不到缓存数据
	 * 				就去数据库中查询并将其缓存，并且将旧数据删除（如果有）并添加新数据
	 * @param dictType 字典类型
	 * @param dictNumber 字典值
	 * @return
	 * @author pengwz
	 * @date 2019年12月21日 上午8:50:55
	 */
	public static String queryName(Object dictType,Integer dictNumber) {
		log.info("---------当前查询的字典类型："+dictType+",字典值："+dictNumber+"---------");
		String dictName = innerQueryDictName(dictType, dictNumber);
		String dictTypeUpper = dictType.toString().toUpperCase();
		//如果缓存中没有该数据，就去数据库查询并缓存起来，如果数据库还查询不到，则返回null
		if(dictName == null) {
			List<AuditDictionaryInfoTable> dictTypeList = queryDB(dictTypeUpper);
			if(dictTypeList == null || dictTypeList.isEmpty()) {
				try {
					throw new DataStaleException(ResultCode.DATA_ERROR,"在字典缓存和数据库中找不到字典类型为'"+
							dictTypeUpper+"'的数据，请检查字典类型是否存在");
				} catch (DataStaleException e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			} else {
				//删除缓存中该类型的旧数据，无论其是否存在
				//毕竟都从数据库里查出来了,就把旧数据删了吧
				DICT_CACHE_MAP.remove(dictTypeUpper);
				Map<String,Integer> map = new HashMap<String, Integer>();
				String returnName = null;
				for (AuditDictionaryInfoTable table : dictTypeList) {
					map.put(table.getDictName(), table.getDictNumber());
					if(table.getDictNumber() == dictNumber);
					returnName = table.getDictName();
				}
				DICT_CACHE_MAP.put(dictTypeUpper, map);
				if(returnName == null) {
					try {
						throw new DataStaleException(ResultCode.DATA_ERROR,"成功查询并缓存了字典类型为'"+
								dictTypeUpper+"'的数据，但是无法匹配字典值为'"+dictNumber+"'的数据");
					} catch (DataStaleException e) {
						log.error(e.getMessage());
						e.printStackTrace();
					}
				}
				addCount(dictTypeUpper);
				log.info("---------当前查询返回的字典类型：'"+dictTypeUpper+"',字典值：'"+dictNumber+"',数据来源：[数据库]"+"---------");
				return returnName;
			}
		}
		addCount(dictTypeUpper);
		log.info("---------当前查询返回的字典类型：'"+dictTypeUpper+"',字典值：'"+dictNumber+"',数据来源：[系统缓存]"+"---------");
		return dictName;
	}

	/**
	 *
	 * @Description 查询当前缓存中的所有数据
	 * @return
	 * @author pengwz
	 * @date 2019年12月20日 上午11:40:12
	 */
	public static List<DictEntity> queryAll() {
		List<DictEntity> dictList = new ArrayList<DictEntity>();
		for(Entry<String,Map<String, Integer>> entry : DICT_CACHE_MAP.entrySet()) {
			DictEntity entity = new DictEntity();
			entity.setDictTableName(entry.getKey());
			Map<String,Integer> dictValueMap = new HashMap<String, Integer>();
			for (Entry<String,Integer> en : entry.getValue().entrySet()) {
				dictValueMap.put(en.getKey(), en.getValue());
				entity.setDictValue(dictValueMap);
			}
			dictList.add(entity);
		}
		return dictList;
	}

	/**
	 * @Description 返回已缓存的字典类型长度
	 * @return
	 * @author pengwz
	 * @date 2019年12月21日 上午9:03:00
	 */
	public static Integer size() {
		return DICT_CACHE_MAP.size();
	}
	/**
	 *
	 * @Description 删除指定的字典类型
	 * @param dictTableName
	 * @author pengwz
	 * @date 2019年12月22日 上午2:14:23
	 */
	public static void remove(String dictTableName) {
		DICT_CACHE_MAP.remove(dictTableName);
	}
	/**
	 * 清空所有缓存
	 * @Description
	 * @author pengwz
	 * @date 2019年12月20日 下午2:45:27
	 */
	public static void clear() {
		DICT_CACHE_MAP.clear();
		log.info("---------已清除所有字典缓存项---------");
	}
	/**
	 *
	 * @Description 等待指定的时间后，清空所有缓存，该方法不会阻塞主程序的正常执行
	 * @param timeout 等待的毫秒值
	 * @author pengwz
	 * @date 2019年12月21日 上午8:18:53
	 */
	public static void clear(long timeout) {
		new Thread(()-> {
			try {
				TimeUnit.MILLISECONDS.sleep(timeout);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			DICT_CACHE_MAP.clear();
		}).start();
	}
	/**
	 * @Description 添加计数器,为数据清理做准备
	 * @param dictType
	 * @author pengwz
	 * @date 2019年12月22日 上午3:49:03
	 */
	private static void addCount(String dictType) {
		Integer count = COUNTER.get(dictType);
		if(count != null) {
			COUNTER.put(dictType, count+1);
		}else {
			COUNTER.put(dictType, 1);
		}
	}
	/**
	 * 根据字典类型去数据库中查询字典数据
	 * @return 返回字典类型相关的字典信息,如果没有查询到则返回null
	 * @author pengwz
	 * @date 2019年12月26日 下午10:20:01
	 */
	private static List<AuditDictionaryInfoTable> queryDB(Object dictType){
		if(dictType != null) {
			String dictTypeUpper = String.valueOf(dictType).toUpperCase();
			List<AuditDictionaryInfoTable> dictTypeList =
					dictCacheService.queryDictByDictType(dictTypeUpper);
			return dictTypeList;
		}else {
			return null;
		}
	}
	/**
	 * 在缓存中查询字典类型下所有数据
	 * @param dictType
	 * @return
	 * @author pengwz
	 * @date 2019年12月26日 下午10:52:47
	 */
	private static Map<String,Integer> innerQuery(Object dictType) {
		String dictTypeUpper = String.valueOf(dictType).toUpperCase();
		return DICT_CACHE_MAP.get(dictTypeUpper);
	}
	/**
	 * 	根据字典类型查询字典数据
	 * @param dictType
	 * @return
	 * @author pengwz
	 * @date 2019年12月29日 下午5:45:26
	 */
	private static Map<String, Integer> queryDictByType(String dictType){
//		Map<String, Integer> map = DICT_CACHE_MAP.get(dictType);
//		if(map != null)
//			return map;
		Map<String, Integer> newMap = new HashMap<String, Integer>();
		List<AuditDictionaryInfoTable> queryDB = queryDB(dictType);
		for (AuditDictionaryInfoTable table : queryDB) {
			newMap.put(table.getDictName(), table.getDictNumber());
		}
		return newMap;
	}
	/**
	 *
	 * @Description 根据传入的字典表名和字典值查询字典名
	 * @param dictTableName 字典表名
	 * @param dictValue 字典名
	 * @return
	 * @author pengwz
	 * @date 2019年12月20日 上午11:39:41
	 */
	private static String innerQueryDictName(Object dictTableName,Integer dictValue) {
		if(dictTableName == null || dictValue == null)
			return null;
		String dictTableUpper = String.valueOf(dictTableName).toUpperCase();
		if(!DICT_CACHE_MAP.containsKey(dictTableUpper))
			return null;
		Map<String, Integer> map = DICT_CACHE_MAP.get(dictTableUpper);
		Set<Entry<String,Integer>> entrySet = map.entrySet();
		for(Entry<String, Integer> entry: entrySet) {
			String key = entry.getKey();
			if(entry.getValue().equals(dictValue))
				return key;
		}
		return null;
	}
	private static class InnerTaskClass extends TimerTask{

		@SuppressWarnings("unused")
		private void calculationClean() {
			//对字典使用频率进行排序,频率越低的排在最前面
			List<Map.Entry<String, Integer>> sortList = new ArrayList<Map.Entry<String, Integer>>(
					COUNTER.entrySet());
			Collections.sort(sortList, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1,
						Map.Entry<String, Integer> o2) {
					return (o1.getValue()-o2.getValue());
				}
			});
			//如果超出限制范围,将使用率最少的清除掉
			for (int i = 0; i < DICT_CACHE_MAP.size()-MAX_CACHE; i++) {
				Entry<String,Integer> ent=sortList.get(i);
				DictCache.remove(ent.getKey());
				//缓存清除后,也要把对应的计数器清掉
				COUNTER.remove(ent.getKey());
			}
		}
		@Override
		public void run() {
//			if(DICT_CACHE_MAP.size() > MAX_CACHE) {
//				//如果实际缓存容量不对等计数器容量，说明数据有偏差，直接清空缓存
//				if(COUNTER.size() != DICT_CACHE_MAP.size()) {
//					log.info("---------实际缓存容量："+(DICT_CACHE_MAP.size())+",计数器计算容量："+COUNTER.size()
//					+",数据有偏差，即将清空所有缓存---------");
//					DictCache.clear();
//				}else {
//					int rows = DICT_CACHE_MAP.size()-MAX_CACHE;
//					calculationClean();
//					log.info("---------成功移除"+rows+"项不常用字典类型缓存---------");
//				}
//			}else {
//				log.info("---------当前字典缓存"+DICT_CACHE_MAP.size()+"项,无需清理---------");
//			}
			//直接清空缓存？还是咋地？
			DictCache.clear();
			COUNTER.clear();
		}
	}
	/**
	 * 暂定每天凌晨一点查缓存数据
	 * @Description
	 * @author pengwz
	 * @date 2019年12月22日 上午4:19:50
	 */
	private static void cleanTasks() {
		Timer timer = new Timer();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,1);
		calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE),1,0,0);
		long timeInterval = 24 * 60 * 60 * 1000;
		timer.scheduleAtFixedRate(new InnerTaskClass(), calendar.getTime(), timeInterval);
	}

}


