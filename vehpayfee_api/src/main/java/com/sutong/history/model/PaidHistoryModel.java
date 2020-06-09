/**
 * @Description: 客户逃费实体
 * @ClassName: RunFee
 * @author： Mr.Kong
 * @date: 2019/12/13 15:13
 * @Version： 1.0
 */
package com.sutong.history.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class PaidHistoryModel implements Serializable {
    /** 主键*/
    private Integer id;
    /** 车牌号*/
    private String carNum;
    /** 车牌颜色*/
    private String licensePlateColor;
    /** 待补费金额*/
    private String toBePaidAmount;
    /** 客户名称（OBU内）*/
    private String customerName;
    /** 补费时间*/
    private String paidTime;
    /** 补费金额*/
    private String paidAmount;
    /** 支付平台信息反馈*/
    private String paymentFormation;
    /** 支付状态*/
    private String paidStatus;
    /** 发票号码*/
    private String invoiceNum;
    /** 发送通知状态， 1 未通知， 2 已通知*/
    private String sendSMSStatus;
    /** 客户电话*/
    private String customerPhone;
}
