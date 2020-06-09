package com.sutong.model;

import lombok.Data;

/**
 * @ClassName PaidManualNoticeModel
 * @Description 客户补费通知手动
 * @Author ly
 * @Date 2019/12/17 14:27
 */
@Data
public class PaidManualNoticeModel {
    /**工单编码*/
    private String orderId;
    /**车牌号*/
    private String vehicleId;
    /**姓名*/
    private String payBackName;
    /**联系方式*/
    private String phoneNum;
    /**补费金额*/
    private int payBackFee;
    /**补费时间*/
    private String paymentTime;
    /**是否补费成功*/
    private String confirmedPayBackFee;

}