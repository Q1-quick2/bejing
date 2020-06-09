package com.sutong.bjstjh.entity;

import java.util.Map;

/**   
 * @ClassName:  DictEntity   
 * @Description:  
 * @author: pengwz
 * @date:   2019年12月20日 上午10:36:08      
 */
public class DictEntity {
	
	/**字典表名*/
	private String dictTableName;
	/**字典数据值*/
	private Map<String,Integer> dictValue;
	
	public String getDictTableName() {
		return dictTableName;
	}
	public void setDictTableName(String dictTableName) {
		this.dictTableName = dictTableName;
	}
	public Map<String, Integer> getDictValue() {
		return dictValue;
	}
	public void setDictValue(Map<String, Integer> dictValue) {
		this.dictValue = dictValue;
	}
	@Override
	public String toString() {
		return "DictEntity [dictTableName=" + dictTableName + ", dictValue=" + dictValue + "]";
	}
	
	
	
	
	
}
