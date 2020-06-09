package com.sutong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.annotation.Comment;
import com.sutong.bjstjh.annotation.Dictionary;
import com.sutong.bjstjh.annotation.Tables;
import com.sutong.bjstjh.exception.BaseException;
import com.sutong.bjstjh.exception.BreakRulesException;
import com.sutong.bjstjh.exception.IllegalParameterException;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.bjstjh.util.ObjectUtils;
import com.sutong.bjstjh.util.POIUtils;
import com.sutong.bjstjh.util.StringUtils;
import com.sutong.cache.DictCache;
import com.sutong.service.IExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @ClassName:  ExcelServiceImpl
 * @Description:
 * @author: pengwz
 * @date:   2019年12月28日 上午11:45:54
 */
@Service
public class ExcelServiceImpl implements IExcelService{

	private static final Logger log = LoggerFactory.getLogger(ExcelServiceImpl.class);

	@Override
	public <T extends Object> List<T> setFileds(T t,Map<String,List<Object>> excelMap,
			int status) throws Exception {
		if(t == null ||  excelMap == null || excelMap.isEmpty() ||
				status < StrictState || status > Relaxed) {
			throw new IllegalParameterException(ResultCode.DATA_ERROR,"所必须的参数不合法");
		}
		List<String> commentList = getAttributes(t);
		List<String> headList = getAttributes(excelMap);
		Map<String, List<String>> checkList = ObjectUtils.checkList(commentList, headList,status);
		if(checkList.isEmpty()) {
			//数据一致,直接转对象
			return turnObject(t, excelMap, getOriginal(t));
		}else {
			//如果设定宽松状态,尝试继续转对象
			if(status == Relaxed) {
				return turnObject(t, excelMap, getOriginal(t));
			}
			//如果设定的是严格状态,直接抛出异常
			throw new BaseException(ResultCode.ERROR,"-----------excel转对象失败-------------");
		}
	}



	@Override
	public <T> List<T> importExcel(MultipartFile excelFile, T t, int status) throws Exception {
		Map<String, List<Object>> caseExcelMap = POIUtils.caseExcelMap(excelFile);
		List<T> setFileds = setFileds(t, caseExcelMap, status);
		return setFileds;
	}



	@Override
	public <T> Void exportExcel(T t, String fileName, String sheetName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	/**
	 * 		将excel数据解析为实体类对象
	 * @param <T>
	 * @param obj
	 * @param excelMap
	 * @param commentMap
	 * @return
	 * @throws Exception
	 * @author pengwz
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @date 2019年12月28日 下午5:41:32
	 */
	@SuppressWarnings("unchecked")
	private <T extends Object> List<T> turnObject(T t,Map<String,List<Object>> excelMap,
			Map<String, Field> commentMap) throws InstantiationException, IllegalAccessException {
		Class<?> cls = t.getClass();
		Method[] methods = cls.getDeclaredMethods();
		List<T> list = new ArrayList<T>();
		//获取最长的excel值,设置初始值是0
		int maxIndex = 0;
		for (Entry<String, List<Object>> entry: excelMap.entrySet()) {
			//如果下一列的值长度比上一个大,则将其替换
			int size = entry.getValue().size();
			maxIndex = maxIndex < size ? size : maxIndex;
		}
		for (int i = 0; i < maxIndex; i++) {
			for (Entry<String, List<Object>> entry: excelMap.entrySet()) {
				//获取excel列名
				String key = entry.getKey().trim();
				//如果通过列名 取 实体类数据注释不为空,说明该条数据存在
				if(commentMap.get(key) != null) {
					Field field = commentMap.get(key);
					for (Method method : methods) {
						String prefix = field.getName().substring(0, 1).toUpperCase();
						String suffix = field.getName().substring(1);
						if (("set"+prefix+suffix).equals(method.getName())) {
							//将excel的具体值取出赋给对象,并且判断下标是否越界
							if(entry.getValue().size() <= i)
								continue;
							Object object = entry.getValue().get(i);
							Object matchType = matchType(field, object);
							try {
								method = cls.getMethod(method.getName(), field.getType());
								method.invoke(t, matchType);
							} catch (Exception e) {
								String errMsg = "无法为"+field.getName()+"属性赋值;错误数据:"+object.toString()+
										";属性类型:"+field.getType()+";Excel类型:"+object.getClass()+
										";错误信息:"+e.getMessage()+";对应全部属性:"+t.toString();
								log.error(errMsg);
								e.printStackTrace();
								throw new IllegalParameterException(ResultCode.DATA_ERROR, errMsg);
							}
						}
					}
				}
			}
			//尝试设置主键
			setVOPK(t);
			list.add(t);
			//切断引用
			t = (T) t.getClass().newInstance();
		}
		return list;
	}
	/**
	 * 		类型转换和字典转值操作
	 * @param field 实体类属性
	 * @param value exel值
	 * @return
	 * @author pengwz
	 * @date 2019年12月29日 下午3:55:06
	 */
	private Object matchType(Field field,Object value){
		if(value == null)
			return null;
		Class<?> type = field.getType();
		//先判断该属性是否为字典
		Dictionary dict = field.getAnnotation(Dictionary.class);
		if(dict != null && !(dict.toString().trim().equals(""))) {
			if(!(String.valueOf(value).trim().equals(""))) {
				//是字典
				String dictType = dict.value().trim().toUpperCase();
				Integer dictValue = DictCache.queryValue(dictType, String.valueOf(value));
				if(dictValue == null)
					log.error( "无法匹配字典: 字典类型:"+dictType+",字典名: "+value);
				value = dictValue;
			} else {
				value = null;
			}
		} else {
			//不是字典
			if(type != value.getClass()) {
				//以下列出常用类型
				String realTypeVO = type.getName().toUpperCase();
				switch (realTypeVO) {
				case "JAVA.LANG.BYTE":
					value = Byte.valueOf(String.valueOf(value));
					break;
				case "JAVA.LANG.SHORT":
					value = Short.valueOf(String.valueOf(value));
					break;
				case "JAVA.LANG.INTEGER":
					value = Integer.valueOf(String.valueOf(value));
					break;
				case "JAVA.LANG.LONG":
					value = Long.valueOf(String.valueOf(value));
					break;
				case "JAVA.LANG.FLOAT":
					value = Float.valueOf(String.valueOf(value));
					break;
				case "JAVA.LANG.DOUBLE":
					value = Double.valueOf(String.valueOf(value));
					break;
				//case "JAVA.LANG.CHARACTER":
					//应该不会出现实体类还有char的吧
					//break;
				case "JAVA.LANG.BOOLEAN":
					value = Double.valueOf(String.valueOf(value));
					break;
				case "JAVA.LANG.STRING":
					value = String.valueOf(value);
					break;
				case "JAVA.UTIL.DATE":
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						value = sdf.parse(String.valueOf(value));
//						value = sdf.format(value);
					} catch (ParseException e) {
						log.error(e.getMessage());
						e.printStackTrace();
						throw new BreakRulesException(ResultCode.DATA_ERROR,e.getMessage());
					}
					break;
				default:
					log.error("未被匹配的类型 : "+type.getClass());
					throw new BreakRulesException(ResultCode.DATA_ERROR,"未被匹配的类型 : "+type.getClass());
				}
			}
		}
		return value;
	}



	/**
	 * 	返回实体类中注释的内容
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author pengwz
	 * @date 2019年12月28日 上午11:47:57
	 */
	private List<String> getAttributes(Object obj) throws Exception {
		Map<String, Field> original = getOriginal(obj);
		List<String> commentList = new ArrayList<String>();
		for (Entry<String, Field> map: original.entrySet()) {
			commentList.add(map.getKey());
		}
		return commentList;
	}
	/**
	 * 	将excel数据的key(表头)取出,转换为List
	 * @param map
	 * @return
	 * @throws Exception
	 * @author pengwz
	 * @date 2019年12月28日 上午11:48:23
	 */
	private List<String> getAttributes(Map<String,List<Object>> map) throws Exception {
		List<String> headList = new ArrayList<String>();
		for (Entry<String, List<Object>> entry: map.entrySet()) {
			headList.add(entry.getKey());
		}
		return headList;
	}

	/**
	 * 	获取注释和字段属性 , 只返回符合comment要求的字段 ,key为注释内容,value为Filed
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author pengwz
	 * @date 2019年12月28日 上午11:48:38
	 */
	private Map<String,Field> getOriginal(Object obj) throws Exception {
		if(obj == null)
			throw new IllegalParameterException(ResultCode.DATA_ERROR,"所必须的参数为null");
		Class<?> cls = obj.getClass();
		Field[] declaredFields = cls.getDeclaredFields();
		Map<String,Field> map = new HashMap<>();
		for (int i = 0; i < declaredFields.length; i++) {
			String fileName = declaredFields[i].getName();
			Comment comment = cls.getDeclaredField(fileName).getDeclaredAnnotation(Comment.class);
			if(comment != null) {
				String trim = comment.value().trim();
				if(trim.equals(""))
					throw new IllegalParameterException(ResultCode.DATA_ERROR,
							declaredFields[i].getName()+"属性注释内容禁止为空字符串");
				map.put(trim,declaredFields[i]);
			}
		}
		if(map.isEmpty()) {
			throw new BreakRulesException(ResultCode.DATA_ERROR,"参数类"+cls.toString()
			+"在获取属性字段时失败,检查属性是否正确添加了@comment注解");
		}
		return map;
	}
	/**
	 * 	设置物理表主键,如果设置失败,抛出异常,但不会影响程序运行
	 * @param obj
	 * @author pengwz
	 * @date 2019年12月28日 上午11:48:53
	 */
	private void setVOPK(Object obj) {
		Class<? extends Object> cls = obj.getClass();
		Tables annotation = cls.getAnnotation(Tables.class);
		if(annotation == null)
			return;
		String vopri = annotation.vopri().trim();
		try {
			if(vopri.equals(""))
				throw new IllegalParameterException(ResultCode.ERROR,"-----无法为主键赋值-----");
			Field field = cls.getDeclaredField(vopri);
			Method[] methods = cls.getDeclaredMethods();
			for (Method method : methods) {
				String name = field.getName();
				String prefix = name.substring(0,1).toUpperCase();
				String suffix = name.substring(1);
				if(("set"+prefix+suffix).equals(method.getName())) {
					method.invoke(obj, StringUtils.generateUUID());
				}
			}
		} catch (IllegalParameterException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error("找不到设置主键的方法");
		}
	}

//	public static void main(String[] args) {
//		try {
//			Map<String,List<Object>> excelMap = new HashMap<String, List<Object>>();
//			List<Object> list11 = new ArrayList<Object>();
//			list11.add("文本文件");
//			excelMap.put("文件类别", list11);
//			List<Object> list = new ArrayList<Object>();
//			list.add("AUDIT_TABLE");
//			list.add("AUDIT_TABLE_2");
//			excelMap.put("所属表名", list);
//			List<Object> listt = new ArrayList<Object>();
//			listt.add("HDSLKSVBJ");
//			excelMap.put("所属表名id", listt);
//			List<Object> list2 = new ArrayList<Object>();
//			list2.add("C://test//soft");
//			excelMap.put("文件路径", list2);
//			List<Object> list666 = new ArrayList<Object>();
//			list666.add("27.78");
//			excelMap.put("文件价值", list666);
//			List<Object> list33 = new ArrayList<Object>();
//			list33.add("2019-12-12 12:12:12");
//			excelMap.put("发送时间", list33);
//
//			List<Object> total = new ArrayList<Object>();
//			total.add("666");
//			total.add("222");
//			total.add(12345);
//			excelMap.put("文件总数", total);
//
//			ExcelServiceImpl impl = new ExcelServiceImpl();
//			List<Object> setFileds = impl.setFileds(new AuditFileTable(), excelMap, Relaxed);
//			System.err.println("长度"+setFileds.size());
//			setFileds.forEach(System.out::println);
//			System.err.println("-------------------------------");
//			impl.setVOPK(new AuditFileTable());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
