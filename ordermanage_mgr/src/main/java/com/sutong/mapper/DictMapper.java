package com.sutong.mapper;

import com.sutong.bjstjh.entity.AuditDictionaryInfoTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName:  AuditWorkorderMapper
 * @Description:
 * @author: pengwz
 * @date:   2019年12月19日 上午11:30:38
 */
@Repository
@Mapper
@Component
public interface DictMapper {

	/**
	 *
	 * @Description 根据字典类型去查询当前类型下的所有值
	 * @param type
	 * @return
	 * @author pengwz
	 * @date 2019年12月20日 下午2:29:02
	 */
	List<AuditDictionaryInfoTable> queryDictByDictType(String type);

//	根据字典类型查询字典数字值dictNumber
	Integer doFindDictNumber(@Param("dictType")String dictType,@Param("dictName")String dictName);



}
