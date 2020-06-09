package com.sutong.auditEvidenceResult.service;

import com.sutong.auditEvidenceResult.model.AuditEvidenceResult;

import java.util.List;

/**
 * @Description: (发行,路段)稽核证据管理
 * @author： Mr.Kong
 * @date: 2019/12/16 17:03
 */
public interface AuditEvidenceResultService {
    /**
     * @description: 查询稽核证据集合
     * @auther: Mr.kong
     * @date: 2019/12/17 21:49
     * @Param auditEvidenceResult: 稽核证据实体
     * @return: java.util.List<com.sutong.auditEvidenceResult.model.AuditEvidenceResult>
     **/
    List<AuditEvidenceResult> doFindAuditEvidenceResultList(AuditEvidenceResult auditEvidenceResult);

    int insert(AuditEvidenceResult record);

    int insertSelective(AuditEvidenceResult record);
}
