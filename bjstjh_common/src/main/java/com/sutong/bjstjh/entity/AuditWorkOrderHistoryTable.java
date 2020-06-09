package com.sutong.bjstjh.entity;
/*
 * 历史工单车标不符实体类
 *
 * */

import com.sutong.bjstjh.annotation.Comment;
import com.sutong.bjstjh.annotation.Dictionary;
import com.sutong.bjstjh.annotation.Tables;
import lombok.Data;

import java.io.Serializable;

@Data
@Tables(vopri = "serialId")
public class AuditWorkOrderHistoryTable implements Serializable {

    private static final long serialVersionUID = 1L;

    // (value = "缓冲期")
    private String buffer;
    // (value = "非缓冲期")REMARK_STATUS
    private String unbuffer;

    //@Comment("责任归属")
    private Integer dutyBelong;
    /**
     * 责任归属应该为字典项，建此属性目的是因为excel内责任归属列没有规则，
     * 所以将该字段映射到此处，等待IExcelService.setFileds(... ...)方法返回对象后，
     * 遍历返回的结果取出该字段，拆分后将真正值取出赋给dutyBelong属性即可
     * <p>
     * 在其他业务操作中应将该属性过滤掉，比如对象转json操作
     */
    @Comment("责任归属")
    private String dutyBelongBak;

    /**
     * 主键id
     */
    private String serialId;
    @Comment("序号")
    private String pastWorkOrderId;

    /**
     * @Comment("外查单号年份")
     */
    private String externalOrderNoYear;
    @Comment("外查单号")
    private String externalOrderNo;
    @Comment("事件类别")
    private String eventTypeCode;
    @Comment(" 反馈方")
    private String feedbackParty;
    @Comment("发行营业厅")
    private String issuingAgent;
    @Comment("客编")
    private String customerNo;
    @Comment("客户名称")
    private String customerName;
    @Comment("代办人")
    private String proxymercName;
    @Comment("证件号码")
    private String certificateNo;
    @Comment("联系电话")
    private String contactTelePhoneNo;
    @Comment("行驶证地址")
    private String drivingLicAddress;
    @Comment("发行系统地址")
    private String drivingLicSystemAddress;
    @Comment("标签号")
    private String obuno;
    @Comment("卡号")
    private String etcno;
    @Comment("车号")
    private String vehplateNo;
    /**
     * 车牌颜色(应该为字典)
     */
//    @Comment("车牌颜色")
//    @Dictionary("VEHPLATE_COLORCODE")
    private Integer vehplateColorCode;
    
    @Comment("签约时间")
    private String signDate;
//    @Comment("签约结束时间")
    /*
    *
    * */
    private String signExpireDate;
    @Comment("发行时间")
    private String issueStartTime;
//
    /*
    * @Comment("发行结束时间")
    * */
    private String issueEndTime;
    @Comment("卡禁用时间")
    private String etcDisableDate;
    @Comment("标签禁用时间")
    private String obuDisableDate;
    @Comment("初次通行时间")
    private String firstPassTime;
    @Comment("最后通行时间")
    private String lastPassTime;
    
    
    
    
//    @Comment("分公司提交的证据（违规事件认定单和车道截图/车道视频）")
    private String proof;
    
    @Comment("补款金额(元)")
    private Double sumPay;
    
    @Comment("补缴时间")
    private String sumTime;
   
    // 字典项开始
    @Comment("实际通行")
    @Dictionary("ACTUAL_TRAFFIC_TYPE")		// 1
    private Integer actualTrafficType;
    @Comment("是否已通知客户")
    @Dictionary("INFORM_CUSTOMERS_FLAG")	// 2
    private Integer informCustomersFlag;
    @Comment("是否已下发告知书/律师函")
    @Dictionary("HAS_BEEN_ISSUED")			// 3
    private Integer notificationBookFlag;
    @Comment("是否有客户资料")
    @Dictionary("YOU_OR_WU")				// 4
    private Integer customerInfoFlag;	
    @Comment("实发")
    @Dictionary("ACTUAL_PAYMENT_TYPE")		// 5 
    private Integer actualPaymentType;
    @Comment("补费状态")
    @Dictionary("ORDER_STATUS")				// 6
    private Integer status;
    @Comment("变更状态")
    @Dictionary("CHANGE_STATUS")			// 7
    private Integer changeStatus;
    @Comment("标签状态")
    @Dictionary("LABEL_STATUS")				// 8
    private Integer obuAccountStatus;
    @Comment("速通卡状态")
    @Dictionary("LABEL_STATUS")				// 9
    private Integer etcStatusList;
    @Comment("车牌黑名单状态")
    @Dictionary("VEHPLATE_BLACK_STATUS")	// 10
    private Integer vehStatusList;
    // 字典项结束
    
    /**
     * 删除标识(0:否，1:是)
     */
    private Integer rmFlag;
//    @Comment("创建时间")
    private String createTime;
//    @Comment("更新时间")
    private String updateTime;
//    @Comment("核算通行")
    private String accountPassAge;
//    @Comment("备注")
    private String remarkStatus;
//    @Comment("车辆所有人")
    private String vehOwner;
//    @Comment("客户地址")
    private String customerAddress;
//    @Comment("重复次数")
    private Integer repeatNumber;
//    @Comment("短信发送时间")
    private String smsSendTime;
//    @Comment("客服跟踪")
    private String customerTrack;



    public AuditWorkOrderHistoryTable() {
    }

    public AuditWorkOrderHistoryTable(String serialId, String pastWorkOrderId, String externalOrderNoYear,
                                      String externalOrderNo, String eventTypeCode, String feedbackParty, String issuingAgent,
                                      String customerNo, String customerName, String proxymercName, String certificateNo,
                                      String contactTelePhoneNo, String drivingLicAddress, String drivingLicSystemAddress,
                                      String obuno, String etcno, String vehplateNo, Integer vehplateColorCode, Integer actualPaymentType,
                                      Integer actualTrafficType, String signDate, String signExpireDate, String issueStartTime,
                                      String issueEndTime, String etcDisableDate, String obuDisableDate, String firstPassTime,
                                      String lastPassTime, Integer dutyBelong, Integer informCustomersFlag, Integer notificationBookFlag,
                                      Integer customerInfoFlag, String proof, Double sumPay, String sumTime, Integer status,
                                      Integer obuAccountStatus, Integer etcStatusList, Integer vehStatusList, Integer rmFlag,
                                      String createTime, String updateTime, String accountPassAge, String remarkStatus) {
        this.serialId = serialId;
        this.pastWorkOrderId = pastWorkOrderId;
        this.externalOrderNoYear = externalOrderNoYear;
        this.externalOrderNo = externalOrderNo;
        this.eventTypeCode = eventTypeCode;
        this.feedbackParty = feedbackParty;
        this.issuingAgent = issuingAgent;
        this.customerNo = customerNo;
        this.customerName = customerName;
        this.proxymercName = proxymercName;
        this.certificateNo = certificateNo;
        this.contactTelePhoneNo = contactTelePhoneNo;
        this.drivingLicAddress = drivingLicAddress;
        this.drivingLicSystemAddress = drivingLicSystemAddress;
        this.obuno = obuno;
        this.etcno = etcno;
        this.vehplateNo = vehplateNo;
        this.vehplateColorCode = vehplateColorCode;
        this.actualPaymentType = actualPaymentType;
        this.actualTrafficType = actualTrafficType;
        this.signDate = signDate;
        this.signExpireDate = signExpireDate;
        this.issueStartTime = issueStartTime;
        this.issueEndTime = issueEndTime;
        this.etcDisableDate = etcDisableDate;
        this.obuDisableDate = obuDisableDate;
        this.firstPassTime = firstPassTime;
        this.lastPassTime = lastPassTime;
        this.dutyBelong = dutyBelong;
        this.informCustomersFlag = informCustomersFlag;
        this.notificationBookFlag = notificationBookFlag;
        this.customerInfoFlag = customerInfoFlag;
        this.proof = proof;
        this.sumPay = sumPay;
        this.sumTime = sumTime;
        this.status = status;
        this.obuAccountStatus = obuAccountStatus;
        this.etcStatusList = etcStatusList;
        this.vehStatusList = vehStatusList;
        this.rmFlag = rmFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.accountPassAge = accountPassAge;
        this.remarkStatus = remarkStatus;
    }
}