package com.sutong.bjstjh.entity;

import java.io.Serializable;

/**   
 * @ClassName:  AuditDictionaryInfoTable   
 * @Description:  字典实体类
 * @author: pengwz
 * @date:   2019年12月20日 下午2:01:21      
 */
public class AuditDictionaryInfoTable implements Serializable{

	private static final long serialVersionUID = 5615521671739611403L;
	
	private String 	dictId;		    //主键，值为uuid
	private String 	dictType;		//字典名称/字典类型（如：事件类型、颜色类型）
	private Integer	dictNumber;	    //字典值
	private String 	dictName;		//字典名
	private Integer	dictValue;		//
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public Integer getDictNumber() {
		return dictNumber;
	}
	public void setDictNumber(Integer dictNumber) {
		this.dictNumber = dictNumber;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public Integer getDictValue() {
		return dictValue;
	}
	public void setDictValue(Integer dictValue) {
		this.dictValue = dictValue;
	}
	
	


}
