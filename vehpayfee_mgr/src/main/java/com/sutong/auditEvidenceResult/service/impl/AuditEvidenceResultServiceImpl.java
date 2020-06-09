package com.sutong.auditEvidenceResult.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.auditEvidenceResult.mapper.AuditEvidenceResultMapper;
import com.sutong.auditEvidenceResult.model.AuditEvidenceResult;
import com.sutong.auditEvidenceResult.service.AuditEvidenceResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: (发行,路段)稽核证据管理
 * @author： Mr.Kong
 * @date: 2019/12/16 17:03
 */
@Component
@Service
public class AuditEvidenceResultServiceImpl implements AuditEvidenceResultService {

    @Autowired
    private AuditEvidenceResultMapper auditEvidenceResultMapper;
    /**
     * @description: 查询稽核证据集合
     * @auther: Mr.kong
     * @date: 2019/12/17 21:49
     * @Param auditEvidenceResult: 稽核证据实体
     * @return: java.util.List<com.sutong.auditEvidenceResult.model.AuditEvidenceResult>
     **/
    @Override
    public List<AuditEvidenceResult> doFindAuditEvidenceResultList(AuditEvidenceResult auditEvidenceResult) {
        return auditEvidenceResultMapper.doFindAuditEvidenceResultList(auditEvidenceResult);
    }

    @Override
    public int insert(AuditEvidenceResult record) {
        return auditEvidenceResultMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditEvidenceResult record) {
        return auditEvidenceResultMapper.insertSelective(record);
    }
}
