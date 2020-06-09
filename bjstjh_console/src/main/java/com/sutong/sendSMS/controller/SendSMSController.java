package com.sutong.sendSMS.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.auditCodeTable.model.AuditCodeTable;
import com.sutong.auditCodeTable.service.AuditCodeTableService;
import com.sutong.auditSmsNotification.model.AuditSmsNotification;
import com.sutong.auditSmsNotification.service.AuditSmsNotificationService;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.StringUtils;
import com.sutong.runfee.model.RunFee;
import com.sutong.runfee.service.RunFeeService;
import com.sutong.transfer.TransferSendSMS;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description: 发送短信通知管理
 * @author： Mr.Kong
 * @date: 2019/12/21 19:33
 */
@Api(tags = "发送短信通知-接口管理")
@CrossOrigin
@RestController
public class SendSMSController {

    private static final Logger logger = LoggerFactory.getLogger(SendSMSController.class);

    private static final String CAR_COLOUR="SYS_NUMBERPLATE_TYPE";//车型

    private static final String ROAD_PART="SYS_ROAD_PART_TYPE";//车型

    @Autowired
    private TransferSendSMS transferSendSMS;
    @Reference
    private RunFeeService runFeeService;
    @Reference
    private AuditSmsNotificationService auditSmsNotificationService;
    @Reference
    private AuditCodeTableService auditCodeTableService;

    /**
     * @description: 历史补费-客户补费通知
     * @auther: Mr.kong
     * @date: 2019/12/21 19:43
     * @Param paidManualNoticeModel:
     * @return: com.sutong.bjstjh.result.Result
     **/
    @ApiOperation(value = "历史客户补费通知接口")
    @GetMapping("/send/payHistorySMS")
    public Result searchFeeRecord(String smsId){
        if (StringUtils.isEmpty(smsId)){
            return Result.error().message("主键smsId不能为空");
        }
        AuditSmsNotification auditSmsNotification = auditSmsNotificationService.selectByPrimaryKey(smsId);
        String status = auditSmsNotification.getStatus();
        String orderStatus="";
        switch (status){
            case "BD":
                orderStatus="缴费成功";
                break;
            default:
                orderStatus="缴费失败";
                break;
        }
        String content = "尊敬的车主您好,您在乐速通"+orderStatus+",感谢您的支持!";
        Map<String,Object> result = transferSendSMS.sendSMS(auditSmsNotification.getPayerPhone(),content);
        if(result.get("success").equals(true)){
            auditSmsNotificationService.updateSendSMSStatus(smsId);
        }else {
            return Result.error().message("发送通知失败!");
        }
        return Result.ok().message("发送成功！");
    }


    /**
     * @description: 补费管理-客户逃费通知
     * @auther: Mr.kong
     * @date: 2019/12/21 19:45
     * @Param runFee: 客户逃费实体
     * @return: com.sutong.bjstjh.result.Result
     **/
    @ApiOperation(value = "客户逃费通知接口")
    @GetMapping("/send/runFeeSMS")
    public Result sendRunFeeSMS(String orderId){
        if (StringUtils.isEmpty(orderId)){
            return Result.error().message("工单编号不能为空！");
        }
        RunFee runFee = runFeeService.doFindRunFeeInfo(Integer.valueOf(orderId));
        String vehicleColour = runFee.getVehicleColour();
        AuditCodeTable nameByCodeAndGenName = auditCodeTableService.getNameByCodeAndGenName(vehicleColour, CAR_COLOUR);
        vehicleColour=nameByCodeAndGenName.getName();
        String audRoad = runFee.getAudRoad();
        AuditCodeTable nameByCodeAndGenName1 = auditCodeTableService.getNameByCodeAndGenName(audRoad, ROAD_PART);
        audRoad=nameByCodeAndGenName1.getName();
        String content ="尊敬的车主您好,您的车牌号："+runFee.getVehicleId()+",车牌颜色："+vehicleColour
                +"在"+audRoad+"路段，产生了逃费记录！";
        Map<String, Object> result = transferSendSMS.sendSMS(runFee.getPhoneNumber(), content);
        if(result.get("success").equals(true)){
            runFeeService.doUpdateSendSmsStatus(orderId);
        }else {
            return Result.error().message("发送通知失败!");
        }
        return Result.ok().message("成功发送！");
    }

    /**
     * @description: 补费管理-客户补费通知
     * @auther: Mr.kong
     * @date: 2019/12/21 19:45
     * @Param runFee: 客户逃费实体
     * @return: com.sutong.bjstjh.result.Result
     **/
    @ApiOperation("客户补费通知接口")
    @GetMapping("/send/sendPayFeeSMS")
    public Result sendPayFeeSMS(String smsId){
        if (StringUtils.isEmpty(smsId)){
            return Result.error().message("主键smsId不能为空");
        }
        AuditSmsNotification auditSmsNotification = auditSmsNotificationService.selectByPrimaryKey(smsId);
        String status = auditSmsNotification.getStatus();
        String orderStatus="";
        switch (status){
            case "BD":
                orderStatus="缴费成功";
                break;
            default:
                orderStatus="缴费失败";
                break;
        }
        String content = "尊敬的车主您好,您在乐速通"+orderStatus+",感谢您的支持!";
        Map<String,Object> result = transferSendSMS.sendSMS(auditSmsNotification.getPayerPhone(),content);
        if(result.get("success").equals(true)){
            auditSmsNotificationService.updateSendSMSStatus(smsId);
        }else {
            return Result.error().message("发送通知失败!");
        }
        auditSmsNotificationService.updateSendSMSStatus(smsId);
        return Result.ok().message("成功发送！");
    }
}
