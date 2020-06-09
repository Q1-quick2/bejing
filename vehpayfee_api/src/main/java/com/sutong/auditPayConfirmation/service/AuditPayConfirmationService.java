package com.sutong.auditPayConfirmation.service;

import com.sutong.auditPayConfirmation.model.AuditPayConfirmation;

/**
 * @Description: 补费确认单信息-接口管理
 * @author： Mr.Kong
 * @date: 2020/1/6 10:30
 */
public interface AuditPayConfirmationService {

    String dateFormat(String dateStr);

    AuditPayConfirmation doFindAuditPayConfirmationInfo(String payRfid);

    int insert(AuditPayConfirmation record);

    int insertSelective(AuditPayConfirmation record);

}
