package com.sutong.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: FeeResult
 * @Description: 补费结果实体
 * @Author: Mr.Su
 * @Date: 2019/12/16 16:02
 * @Version V1.0
 **/
@Data
@ToString
public class FeeResult implements Serializable{
    //工单编号
    private String orderId;
    //车牌号
    private String vehicleId;
    //车牌颜色
    private String vehicleColor;
    //用户id
    private String userId;
    //补费人姓名
    private String paybackName;
    //补费人联系方式
    private String phoneNum;
    //是否补费成功 1 已补费 2未补费
    private Integer confirmedPaybackFee;
    //补费金额
    private Integer paybackFee;

    private String paymentTime;

    private String payPhone;

    private String transactionNumber;
    //发送通知状态
    private String sendSmsStatus;
}
