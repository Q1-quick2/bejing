package com.sutong.dodgingtoll.mapper;


import com.sutong.dodgingtoll.model.AuditCodeTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description: 字典数据管理
 * @author： Mr.Kong
 * @date: 2019/12/23 20:01
 */
@Repository
@Component
@Mapper
public interface AuditCodeTableMapper {

    List<AuditCodeTable> doFindAuditCodeTableList(String generalName);

    int deleteByPrimaryKey(String codeId);

    int insert(AuditCodeTable record);

    int insertSelective(AuditCodeTable record);

    String getNameByCodeAndGen(Map<String, Object> map);

    AuditCodeTable selectByPrimaryKey(String codeId);

    int updateByPrimaryKeySelective(AuditCodeTable record);

    int updateByPrimaryKey(AuditCodeTable record);
}
