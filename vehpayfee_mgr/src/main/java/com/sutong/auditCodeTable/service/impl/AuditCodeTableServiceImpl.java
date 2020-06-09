package com.sutong.auditCodeTable.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.auditCodeTable.mapper.AuditCodeTableMapper;
import com.sutong.auditCodeTable.model.AuditCodeTable;
import com.sutong.auditCodeTable.service.AuditCodeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description: 字典数据管理
 * @author： Mr.Kong
 * @date: 2019/12/23 20:11
 */
@Component
@Service
public class AuditCodeTableServiceImpl implements AuditCodeTableService {

    @Autowired
    private AuditCodeTableMapper auditCodeTableMapper;


    @Override
    public AuditCodeTable getNameByCodeAndGenName(String code,String generalName) {
        AuditCodeTable auditCodeTable=new AuditCodeTable();
        auditCodeTable.setCode(code);
        auditCodeTable.setGeneralName(generalName);
        return auditCodeTableMapper.getNameByCodeAndGenName(auditCodeTable);
    }

    @Override
    public String getNameByCodeAndGen(Map<String, Object> map) {
        return auditCodeTableMapper.getNameByCodeAndGen(map);
    }

    @Override
    public List<AuditCodeTable> doFindAuditCodeTableList(String generalName) {
        return auditCodeTableMapper.doFindAuditCodeTableList(generalName);
    }

    @Override
    public int deleteByPrimaryKey(String codeId) {
        return auditCodeTableMapper.deleteByPrimaryKey(codeId);
    }

    @Override
    public int insert(AuditCodeTable record) {
        return auditCodeTableMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditCodeTable record) {
        return auditCodeTableMapper.insertSelective(record);
    }

    @Override
    public AuditCodeTable selectByPrimaryKey(String codeId) {
        return auditCodeTableMapper.selectByPrimaryKey(codeId);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditCodeTable record) {
        return auditCodeTableMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuditCodeTable record) {
        return auditCodeTableMapper.updateByPrimaryKey(record);
    }
}
