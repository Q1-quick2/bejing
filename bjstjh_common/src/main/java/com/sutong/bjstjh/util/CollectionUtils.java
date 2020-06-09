package com.sutong.bjstjh.util;

import com.sutong.bjstjh.exception.BreakRulesException;
import com.sutong.bjstjh.result.ResultCode;

import java.util.*;
import java.util.Map.Entry;

/**
 * @ClassName:  CollectionUtils
 * @Description:
 * @author: pengwz
 * @date:   2020年1月8日 下午3:30:04
 */
public class CollectionUtils {

	/**
	 * 	将传入的集合按照指定的容量进行拆分
	 * @param <T>
	 * @param list
	 * @param record 每个集合包含的最大容量
	 * @return  拆分后的父集合
	 * @author pengwz
	 * @date 2020年1月8日 下午3:30:41
	 */
	public static <T extends Object> List<List<T>> splitList(List<T> list,int record){
		if(list==null||list.isEmpty())
			return null;
		if(record < 1)
			throw new BreakRulesException(ResultCode.ERROR, "每个集合最小容量不应该低于1.");
		int count = (list.size()+record-1)/record;
		List<List<T>> listGroup = new ArrayList<List<T>>();
		int start = 0;
		for (int i = 0; i < count; i++) {
			if(list.size()-start < record) {
				List<T> subList = list.subList(start, list.size());
				listGroup.add(subList);
			}else {
				List<T> subList = list.subList(start, i==0?record:record*(i+1));
				start = i==0?record:record*(i+1);
				listGroup.add(subList);
			}
		}
		return listGroup;
	}

	/**
	 * 	对查询的结果进行分页
	 * @param <T>
	 * @param list
	 * @param pageNumber 当前页码
	 * @param pageSize   每页显示的条数
	 * @return
	 * @author pengwz
	 * @date 2020年1月9日 下午4:28:22
	 */
	public static <T extends Object> List<T> splitList(List<T> list,int pageNumber,int pageSize){
		if(list == null || list.size() == 1)
			return list;
		if(pageNumber < 1 || pageSize < 1)
			throw new BreakRulesException(ResultCode.ERROR,"当前页码数和当前页码展示数不可以小于1.");
		//开始下标
		int start = (pageNumber - 1) * pageSize;
		//结束下标
		int end = start + pageSize > list.size() ? list.size() : start + pageSize ;
		List<T> subList = list.subList(start, end);
		return subList;
	}


	/**
	 * 	根据元素值获取其所在集合的所有下标,如果没有则返回空集合
	 * @param <T>
	 * @param obj 元素值
	 * @param list 该元素所在的集合
	 * @return
	 * @author pengwz
	 * @date 2020年1月10日 上午11:37:13
	 */
	public static <T> List<Integer> indexOfAll(Object obj,List<T> list){
		//将传入的List转为Map,key为List的索引,value为List的值本身
		Map<Integer,T> map = new HashMap<Integer, T>();
		for (int i = 0; i < list.size(); i++) {
				map.put(i, list.get(i));
		}
		List<Integer> indexList = new ArrayList<Integer>();
		if(list.contains(obj)) {
			Set<Entry<Integer,T>> entrySet = map.entrySet();
			for (Entry<Integer, T> entry : entrySet) {
				if(entry.getValue() == null && obj == null) {
					indexList.add(entry.getKey());
				} else if(entry.getValue() == null && obj != null) {
					continue;
				} else {
					if(entry.getValue().equals(obj)) {
						indexList.add(entry.getKey());
					}
				}
			}
		}
		return indexList;
	}

	/**
	 * 	根据元素值获取其所在集合的所有下标,如果没有则返回空集合
	 * 	当泛型指定为String时,所有的""," ","   " .... 空串都会被认定为""
	 * 	换言之所有的空串会被此方法认为同一个元素.
	 * @param <T>
	 * @param obj 元素值
	 * @param list 该元素所在的集合
	 * @return
	 * @author pengwz
	 * @date 2020年1月10日 上午11:37:13
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<Integer> indexOfAllIgnore(Object obj,List<T> list){
		//将传入的List转为Map,key为List的索引,value为List的值本身
		Map<Integer,T> map = new HashMap<Integer, T>();
		for (int i = 0; i < list.size(); i++) {
			//如果元素是String 类型的,就把"" 或" " 或"   " ....  等等认为是同一个东西
			if(list.get(i) instanceof String && list.get(i) != null) {
				map.put(i, (T) list.get(i).toString().trim());
			} else {
				map.put(i, list.get(i));
			}
		}
		List<Integer> indexList = new ArrayList<Integer>();
		//将所有需要比对的参数,如果是空值就全部认定为空串
		if(obj instanceof String && obj != null) {
			obj = obj.toString().trim();
		}
		if(list.contains(obj)) {
			Set<Entry<Integer,T>> entrySet = map.entrySet();
			for (Entry<Integer, T> entry : entrySet) {
				if(entry.getValue() == null && obj == null) {
					indexList.add(entry.getKey());
				} else if(entry.getValue() == null && obj != null) {
					continue;
				} else {
					if(entry.getValue().equals(obj)) {
						indexList.add(entry.getKey());
					}
				}
			}
		}
		return indexList;
	}

	/**
	 * 	获取Excel行数
	 * @param start 当前excel正文开始的位置
	 * @param values	POIUtils解析Map后的values集合
	 * @return
	 * @author pengwz
	 * @date 2020年1月10日 上午9:20:44
	 */
	public static List<String> getRowNumber(int start,Collection<List<Object>> values){
		if(start < 0)
			throw new BreakRulesException(ResultCode.ERROR,"开始值不正确.");
		if(values == null || values.isEmpty())
			throw new BreakRulesException(ResultCode.ERROR,"Excel数据不存在或文件已损坏");
		List<String> list = new ArrayList<String>();
		int asInt = values.stream().mapToInt(List<Object> :: size).max().getAsInt();
		for (int i = start; i < (asInt + start); i++) {
			list.add(i+"");
		}
		return list;
	}
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("haha");
		list.add("hehe");
		list.add("");
		list.add(null);
		list.add("  ");
		list.add("  ");
		list.add(null);
		list.add("  ");
		list.add("  ");
		list.add("            ");
		list.add("  ");
		List<Integer> indexOfAll = indexOfAllIgnore(" ", list);
		System.err.println(indexOfAll);
	}
}




