package com.sutong.auditCodeTable.service;

import com.sutong.auditCodeTable.model.AuditCodeTable;

import java.util.List;
import java.util.Map;

/**
 * @Description: 字典数据管理
 * @author： Mr.Kong
 * @date: 2019/12/23 20:10
 */
public interface AuditCodeTableService {

    AuditCodeTable getNameByCodeAndGenName(String code,String generalName);

    String getNameByCodeAndGen(Map<String,Object> map);

    List<AuditCodeTable> doFindAuditCodeTableList(String generalName);

    int deleteByPrimaryKey(String codeId);

    int insert(AuditCodeTable record);

    int insertSelective(AuditCodeTable record);

    AuditCodeTable selectByPrimaryKey(String codeId);

    int updateByPrimaryKeySelective(AuditCodeTable record);

    int updateByPrimaryKey(AuditCodeTable record);
}
