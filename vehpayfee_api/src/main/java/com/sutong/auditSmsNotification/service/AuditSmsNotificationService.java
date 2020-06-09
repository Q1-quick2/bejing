package com.sutong.auditSmsNotification.service;

import com.github.pagehelper.PageInfo;
import com.sutong.auditSmsNotification.model.AuditSmsNotification;

/**
 * @Description:
 * @author： Mr.Kong
 * @date: 2019/12/31 22:10
 */
public interface AuditSmsNotificationService {

    PageInfo<AuditSmsNotification> doFindHistoryAuditSmsNotificationPage(AuditSmsNotification record);

    PageInfo<AuditSmsNotification> doFindAuditSmsNotificationPage(AuditSmsNotification record);

    int deleteByPrimaryKey(String smsId);

    int insert(AuditSmsNotification record);

    int insertSelective(AuditSmsNotification record);

    AuditSmsNotification selectByPrimaryKey(String smsId);

    int updateByPrimaryKeySelective(AuditSmsNotification record);

    int updateByPrimaryKey(AuditSmsNotification record);

    int updateSendSMSStatus(String smsId);
}
