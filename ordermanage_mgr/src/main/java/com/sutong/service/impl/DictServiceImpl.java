package com.sutong.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.entity.AuditDictionaryInfoTable;
import com.sutong.mapper.DictMapper;
import com.sutong.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName:  DictServiceImpl
 * @Description:
 * @author: pengwz
 * @date:   2019年12月20日 下午2:16:00
 */
@Service
public class DictServiceImpl implements IDictService {

	@Autowired
	private DictMapper dictMapper;

	@Override
	public List<AuditDictionaryInfoTable> queryDictByDictType(String type) {
		return dictMapper.queryDictByDictType(type);
	}
//根据字典类型和字段名字返回字典值
	@Override
	public Integer doFindDictNumber(String dictType, String dictName) {
		return dictMapper.doFindDictNumber(dictType,dictName);
	}


}
