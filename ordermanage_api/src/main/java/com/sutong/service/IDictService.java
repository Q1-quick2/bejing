package com.sutong.service;

import com.sutong.bjstjh.entity.AuditDictionaryInfoTable;

import java.util.List;

/**
 * @ClassName:  IDictService
 * @Description:  字典查询
 * @author: pengwz
 * @date:   2019年12月19日 上午11:17:05
 */
public interface IDictService {
	/**
	 *
	 * @Description 根据字典类型去查询当前类型下的所有值
	 * @param type
	 * @return
	 * @author pengwz
	 * @date 2019年12月20日 下午2:05:10
	 */
	List<AuditDictionaryInfoTable> queryDictByDictType(String type);


//	根据字典类型返回字典数字值dictNumber
	Integer doFindDictNumber(String dictType,String dictName);

}
