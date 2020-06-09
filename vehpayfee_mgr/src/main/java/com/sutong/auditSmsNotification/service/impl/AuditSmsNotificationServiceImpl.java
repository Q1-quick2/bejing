package com.sutong.auditSmsNotification.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.auditPastOrderInfo.mapper.AuditPastOrderInfoMapper;
import com.sutong.auditPastOrderInfo.model.AuditPastOrderInfo;
import com.sutong.auditSmsNotification.mapper.AuditSmsNotificationMapper;
import com.sutong.auditSmsNotification.model.AuditSmsNotification;
import com.sutong.auditSmsNotification.service.AuditSmsNotificationService;
import com.sutong.bjstjh.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @author： Mr.Kong
 * @date: 2019/12/31 22:10
 */
@Component
@Service
public class AuditSmsNotificationServiceImpl implements AuditSmsNotificationService {

    @Autowired
    private AuditSmsNotificationMapper auditSmsNotificationMapper;
    @Autowired
    private AuditPastOrderInfoMapper auditPastOrderInfoMapper;



    public void payTimeFormat(List<AuditSmsNotification> auditSmsNotificationList){
        if (ObjectUtils.isNotNull(auditSmsNotificationList)){
            for (AuditSmsNotification auditSmsNotification:auditSmsNotificationList){
                //转换时间格式
                String paymentTime = auditSmsNotification.getPayTime();
                String reg = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
                paymentTime = paymentTime.replaceAll(reg, "$1-$2-$3 $4:$5:$6");
                paymentTime=paymentTime.substring(0, paymentTime.indexOf(" "));
                auditSmsNotification.setPayTime(paymentTime);
            }
            }
    }


    @Override
    public PageInfo<AuditSmsNotification> doFindHistoryAuditSmsNotificationPage(AuditSmsNotification record) {
        PageHelper.startPage(record.getPageNum(),record.getPageSize());
        List<AuditSmsNotification> auditSmsNotifications = auditSmsNotificationMapper.doFindHistoryAuditSmsNotificationPage(record);
        //obuId
        if (ObjectUtils.isNotNull(auditSmsNotifications)){
            for (AuditSmsNotification auditSmsNotification:auditSmsNotifications){
                //设置车牌号
                AuditPastOrderInfo auditPastOrderInfo=new AuditPastOrderInfo();
                auditPastOrderInfo.setTransObuId(auditSmsNotification.getObuId());
                List<AuditPastOrderInfo> auditPastOrderInfos = auditPastOrderInfoMapper.doFindAuditPastOrderInfoPage(auditPastOrderInfo);
                if (ObjectUtils.isNotNull(auditPastOrderInfos) && auditPastOrderInfos.size()>0){
                    auditSmsNotification.setVehicleNumber(auditPastOrderInfos.get(0).getTransVehicleId());
                }
                //转换时间格式
                String paymentTime = auditSmsNotification.getPayTime();
                String reg = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
                paymentTime = paymentTime.replaceAll(reg, "$1-$2-$3 $4:$5:$6");
                paymentTime=paymentTime.substring(0, paymentTime.indexOf(" "));
                auditSmsNotification.setPayTime(paymentTime);
            }
        }
        PageInfo<AuditSmsNotification> pageInfo=new PageInfo<>(auditSmsNotifications);
        return pageInfo;
    }

    @Override
    public PageInfo<AuditSmsNotification> doFindAuditSmsNotificationPage(AuditSmsNotification record) {
        PageHelper.startPage(record.getPageNum(),record.getPageSize());
        List<AuditSmsNotification> auditSmsNotifications = auditSmsNotificationMapper.doFindAuditSmsNotificationPage(record);
        this.payTimeFormat(auditSmsNotifications);
        PageInfo<AuditSmsNotification> pageInfo=new PageInfo<>(auditSmsNotifications);
        return pageInfo;
    }

    @Override
    public int deleteByPrimaryKey(String smsId) {
        return auditSmsNotificationMapper.deleteByPrimaryKey(smsId);
    }

    @Override
    public int insert(AuditSmsNotification record) {
        return auditSmsNotificationMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditSmsNotification record) {
        return auditSmsNotificationMapper.insertSelective(record);
    }

    @Override
    public AuditSmsNotification selectByPrimaryKey(String smsId) {
        return auditSmsNotificationMapper.selectByPrimaryKey(smsId);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditSmsNotification record) {
        return auditSmsNotificationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuditSmsNotification record) {
        return auditSmsNotificationMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateSendSMSStatus(String smsId) {
        return auditSmsNotificationMapper.updateSendSMSStatus(smsId);
    }
}
