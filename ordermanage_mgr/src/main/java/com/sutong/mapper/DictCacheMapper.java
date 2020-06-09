package com.sutong.mapper;

import com.sutong.bjstjh.entity.AuditDictionaryInfoTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName:  DictCacheMapper
 * @Description:
 * @author: pengwz
 * @date:   2019年12月20日 下午5:43:31
 */
@Repository
@Mapper
@Component
public interface DictCacheMapper {
	/**
	 *
	 * @Description
	 * @param type
	 * @return
	 * @author pengwz
	 * @date 2019年12月20日 下午5:35:36
	 */
	List<AuditDictionaryInfoTable> queryDictByDictType(String type);
}
