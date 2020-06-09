package com.sutong.service;

import com.sutong.bjstjh.entity.AuditDictionaryInfoTable;

import java.util.List;

public interface IDictCacheService {
	/**
	 *
	 * @Description 根据字典类型去查询当前类型下的所有值
	 * @param type
	 * @return
	 * @author pengwz
	 * @date 2019年12月20日 下午2:05:10
	 */
	List<AuditDictionaryInfoTable> queryDictByDictType(String type);

}
