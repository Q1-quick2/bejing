package com.sutong.workorder.scheduletask;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.bjstjh.entity.AuditWorkOrderHistoryTable;
import com.sutong.historyOrder.model.AuditLicensePlateDiferentEntity;
import com.sutong.historyOrder.service.VehicleDiscrepancyService;
import com.sutong.transfer.TransferIssue;
import com.sutong.transfer.TransferSendSMS;
import com.sutong.workorder.entity.AuditLogManagementEntity;
import com.sutong.workorder.model.CardLiftingDTO;
import com.sutong.workorder.service.IScheduleService;
import com.sutong.workorder.service.IWorkOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: BlackAndGreyListScheduleTask
 * @Description: 黑名单和灰名单的定时任务
 * @author: lichengquan
 * @date: 2019年12月19日 18:06
 * @Version: 1.0
 */
@Component
@Configuration
@EnableScheduling
public class BlackAndGreyListScheduleTask {


    private static final Logger log = LoggerFactory.getLogger(BlackAndGreyListScheduleTask.class);

    @Reference
    private IScheduleService iScheduleService;
    @Reference
    private VehicleDiscrepancyService vehicleDiscrepancyService;
    @Reference
    private IWorkOrderService iWorkOrderService;

    @Autowired
    private TransferSendSMS transferSendSms;
    @Autowired
    private TransferIssue transferIssue;

//@Scheduled(cron = "0/5 * * * * ?")
//    @Scheduled(fixedRate=5000)

    /**
     * 稽核黑名单全量下载-保存定时任务
     */
    private void blackListScheduleTask() {
        iScheduleService.blackListScheduleTask();
    }

    //@Scheduled(cron = "0/5 * * * * ?")
//    @Scheduled(fixedRate=5000)

    /**
     * 稽核灰名单全量下载 - 保存定时任务
     */
    private void greyListScheduleTask() {
        iScheduleService.greyListScheduleTask();
    }

    //@Scheduled(cron = "0/5 * * * * ?")
    //@Scheduled(fixedRate = 5000)

    /**
     * 未通知客户的工单需发短信通知客户-车标不符-历史工单定时任务
     */
    private void historyOrderScheduleTask() {
        List<AuditWorkOrderHistoryTable> auditWorkOrderHistory = iScheduleService.getAuditWorkOrderHistory();
        log.info("查询未通知结果：{}", auditWorkOrderHistory);
        if (!CollectionUtils.isEmpty(auditWorkOrderHistory)) {
            Map<String, String> historyOrderMap = auditWorkOrderHistory.stream().collect(Collectors.toMap(AuditWorkOrderHistoryTable::getSerialId, AuditWorkOrderHistoryTable::getContactTelePhoneNo));
            String message = "测试发送短信";
            List<String> successKeyIds = this.sendSms(historyOrderMap, message);
            if (!CollectionUtils.isEmpty(successKeyIds)) {
                Integer successNum = iScheduleService.updateInFormCustomersFlag(successKeyIds);
                if (successNum == successKeyIds.size()) {
                    log.info("发送短信成功,发送短信成功的id:{}", successKeyIds);
                }
            } else {
                log.info("发送短信失败");
            }
        } else {
            log.info("无未通知状态的工单");
        }

    }

    //
    //@Scheduled(fixedRate = 5000)

    /**
     * 定时通知发行解禁（车标不符-历史工单）
     * 每天下午16点
     */
    //@Scheduled(cron = "0 22 * * * ? ")
    private void cardLifting() {
        List<AuditWorkOrderHistoryTable> cardLiftingWorkOrder = iScheduleService.getCardLiftingWorkOrder();
        if (!CollectionUtils.isEmpty(cardLiftingWorkOrder)) {
            Map<String, String> historyOrderMap = cardLiftingWorkOrder.stream().collect(Collectors.toMap(AuditWorkOrderHistoryTable::getSerialId, AuditWorkOrderHistoryTable::getEtcno));
            List<String> successKeyIds = this.toCardLifting(historyOrderMap);
            if (!CollectionUtils.isEmpty(successKeyIds)) {
                Integer updateEtcStatus = iScheduleService.updateEtcStatus(successKeyIds);
                if (updateEtcStatus == successKeyIds.size()) {
                    log.info("通知发行解禁成功，成功的历史工单id:{}", successKeyIds);
                }
            } else {
                log.info("解禁失败");
            }
        } else {
            log.info("无通知发行解禁的工单");
        }
    }

//
    //@Scheduled(fixedRate = 5000)
    /**
     * 车牌不符自动解禁
     * 每天下午17点
     */
    //@Scheduled(cron = "0 3 * * * ? ")
    private void vehicleDiscrepancyScheduleTask() {
        List<AuditLicensePlateDiferentEntity> notLiftingEntity = vehicleDiscrepancyService.getNotLiftingEntity();
        if (!CollectionUtils.isEmpty(notLiftingEntity)) {
            log.info("未解禁的车牌不符的信息：{}", notLiftingEntity);
            Map<String, String> vehicleDiscrepancyCollect = notLiftingEntity.stream().collect(Collectors.toMap(AuditLicensePlateDiferentEntity::getSerialId, AuditLicensePlateDiferentEntity::getEtcNumber));
            List<String> successKeyIds = this.toCardLifting(vehicleDiscrepancyCollect);
            if (!CollectionUtils.isEmpty(successKeyIds)) {
                Integer updateEtcStatus = vehicleDiscrepancyService.doUpdateLiftingStatus(successKeyIds);
                if (updateEtcStatus == successKeyIds.size()) {
                    log.info("车牌不符解禁成功的id:{}", successKeyIds);
                }
            } else {
                log.info("解禁失败");
            }
        } else {
            log.info("无需通知的车牌不符信息");
        }
    }

    /**
     * 发短信
     *
     * @param telephoneMap Map<表id,电话号码>
     * @param message      要发送的短信
     * @return
     */
    private List<String> sendSms(Map<String, String> telephoneMap, String message) {
        List<String> successkeyId = new ArrayList<>();
        for (String keyId : telephoneMap.keySet()) {
            AuditLogManagementEntity auditLogManagementEntity = new AuditLogManagementEntity();
            auditLogManagementEntity.setSystemName("稽核工单");
            auditLogManagementEntity.setSystemCode("001");
            auditLogManagementEntity.setInterfaceName("发送短信方法");
            auditLogManagementEntity.setRequestParameterInfo("手机号：" + telephoneMap.get(keyId) + ",短信内容：" + message);
            try {
                Map<String, Object> resultMap = transferSendSms.sendSMS(telephoneMap.get(keyId), message);
                auditLogManagementEntity.setReturnParameterInfo(resultMap.toString());
//                if(!resultMap.containsValue(true)){
//                    log.info("发送短信失败");
//                }else {
//                    successkeyId.add(keyId);
//                }
                successkeyId.add(keyId);
            } catch (Exception e) {
                e.printStackTrace();
                auditLogManagementEntity.setReturnParameterInfo(e.toString());
            }
            iWorkOrderService.doInsertLogManagement(auditLogManagementEntity);
        }
        return successkeyId;
    }

    /**
     * 调用卡解禁
     *
     * @param cardLiftMap Map<keyId,ETC卡号>
     * @return 成功keyIds
     */
    private List<String> toCardLifting(Map<String, String> cardLiftMap) {
        List<String> successKeyIds = new ArrayList<>();
        for (String keyId : cardLiftMap.keySet()) {
            log.info("开始调用卡解禁接口");
            AuditLogManagementEntity auditLogManagementEntity = new AuditLogManagementEntity();
            auditLogManagementEntity.setSystemName("稽核工单");
            auditLogManagementEntity.setSystemCode("002");
            auditLogManagementEntity.setInterfaceName("ETC卡解禁接口");
            auditLogManagementEntity.setRequestParameterInfo("cardNo:" + cardLiftMap.get(keyId));
            try {
                CardLiftingDTO cardLiftingDTO = transferIssue.cardLifting(cardLiftMap.get(keyId));
                auditLogManagementEntity.setReturnParameterInfo(cardLiftingDTO.toString());
                if (cardLiftingDTO.getRetValue()) {
                    successKeyIds.add(keyId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                auditLogManagementEntity.setReturnParameterInfo(e.toString());
            }
            iWorkOrderService.doInsertLogManagement(auditLogManagementEntity);
        }
        return successKeyIds;
    }

}
