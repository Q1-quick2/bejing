package com.sutong.auditSmsNotification.mapper;

import com.sutong.auditSmsNotification.model.AuditSmsNotification;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @author： Mr.Kong
 * @date: 2019/12/31 22:07
 */

@Repository
@Mapper
@Component
public interface AuditSmsNotificationMapper {

    List<AuditSmsNotification> doFindHistoryAuditSmsNotificationPage(AuditSmsNotification record);

    List<AuditSmsNotification> doFindAuditSmsNotificationPage(AuditSmsNotification record);

    int deleteByPrimaryKey(String smsId);

    int insert(AuditSmsNotification record);

    int insertSelective(AuditSmsNotification record);

    AuditSmsNotification selectByPrimaryKey(String smsId);

    int updateByPrimaryKeySelective(AuditSmsNotification record);

    int updateByPrimaryKey(AuditSmsNotification record);

    int updateSendSMSStatus(String smsId);

}
