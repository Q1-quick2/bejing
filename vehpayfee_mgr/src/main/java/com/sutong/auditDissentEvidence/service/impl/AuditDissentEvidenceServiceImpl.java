package com.sutong.auditDissentEvidence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.auditDissentEvidence.mapper.AuditDissentEvidenceMapper;
import com.sutong.auditDissentEvidence.model.AuditDissentEvidence;
import com.sutong.auditDissentEvidence.service.AuditDissentEvidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 异议申请流水证据管理
 * @author： Mr.Kong
 * @date: 2019/12/19 15:08
 */
@Component
@Service
public class AuditDissentEvidenceServiceImpl implements AuditDissentEvidenceService {

    @Autowired
    private AuditDissentEvidenceMapper auditDissentEvidenceMapper;
    /**
     * @description: 查询异议申请流水证据集合
     * @auther: Mr.kong
     * @date: 2019/12/19 16:34
     * @Param dissentId: 异议编码
     * @return: java.util.List<com.sutong.auditDissentEvidence.model.AuditDissentEvidence>
     **/
    @Override
    public List<AuditDissentEvidence> doFindAuditDissentEvidenceList(String dissentId) {
        return auditDissentEvidenceMapper.doFindAuditDissentEvidenceList(dissentId);
    }

    @Override
    public int deleteByPrimaryKey(String dissentId) {
        return auditDissentEvidenceMapper.deleteByPrimaryKey(dissentId);
    }

    @Override
    public int insert(AuditDissentEvidence record) {
        return auditDissentEvidenceMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditDissentEvidence record) {
        return auditDissentEvidenceMapper.insertSelective(record);
    }

    @Override
    public AuditDissentEvidence selectByPrimaryKey(String dissentId) {
        return auditDissentEvidenceMapper.selectByPrimaryKey(dissentId);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditDissentEvidence record) {
        return auditDissentEvidenceMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuditDissentEvidence record) {
        return auditDissentEvidenceMapper.updateByPrimaryKey(record);
    }
}
