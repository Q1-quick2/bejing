package com.sutong.auditDissentEvidence.service;

import com.sutong.auditDissentEvidence.model.AuditDissentEvidence;

import java.util.List;

/**
 * @Description: 异议申请流水证据管理
 * @author： Mr.Kong
 * @date: 2019/12/19 15:07
 */
public interface AuditDissentEvidenceService {

    /**
     * @description: 查询异议申请流水证据集合
     * @auther: Mr.kong
     * @date: 2019/12/19 16:34
     * @Param dissentId: 异议编码
     * @return: java.util.List<com.sutong.auditDissentEvidence.model.AuditDissentEvidence>
     **/
    List<AuditDissentEvidence> doFindAuditDissentEvidenceList(String dissentId);

    int deleteByPrimaryKey(String dissentId);

    int insert(AuditDissentEvidence record);

    int insertSelective(AuditDissentEvidence record);

    AuditDissentEvidence selectByPrimaryKey(String dissentId);

    int updateByPrimaryKeySelective(AuditDissentEvidence record);

    int updateByPrimaryKey(AuditDissentEvidence record);
}
