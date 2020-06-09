package com.sutong.service.impl;

import com.sutong.bjstjh.entity.AuditDictionaryInfoTable;
import com.sutong.mapper.DictCacheMapper;
import com.sutong.service.IDictCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName:  DictCacheServiceImpl
 * @Description:
 * @author: pengwz
 * @date:   2019年12月20日 下午6:53:46
 */
@Service("dictCacheService")
public class DictCacheServiceImpl implements IDictCacheService{

	@Autowired
	private DictCacheMapper dictMapper;

	@Override
	public List<AuditDictionaryInfoTable> queryDictByDictType(String type) {
		return dictMapper.queryDictByDictType(type);
	}

}
