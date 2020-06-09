package com.sutong.bjstjh.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 补费结果表
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepairfeeModel implements Serializable{

    private String vehicleId;               //车牌号
    private String vehicleColour;         //车牌颜色
    private String userId;                //用户id
    private String paybackUser;             //补费人姓名
    private String paybackUserPhone;      //补费人联系方式
    private Integer paybackFee;             //补费金额
    private String paymentTime;         // 缴费时间
    private String payPhone;            // 手机号
    private String transactionNumber;   // 交易流水号


    public String getVehicleColour() {
        return vehicleColour;
    }

    public void setVehicleColour(String vehicleColour) {
        this.vehicleColour = vehicleColour;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaybackUserPhone() {
        return paybackUserPhone;
    }

    public void setPaybackUserPhone(String paybackUserPhone) {
        this.paybackUserPhone = paybackUserPhone;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPayPhone() {
        return payPhone;
    }

    public void setPayPhone(String payPhone) {
        this.payPhone = payPhone;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    private Integer confirmedPaybackFee;    //是否补费成功
    private String confirmedPayback;        //  补费状态

    public String getConfirmedPayback() {
        return confirmedPayback;
    }

    public void setConfirmedPayback(String confirmedPayback) {
        this.confirmedPayback = confirmedPayback;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getPaybackUser() {
        return paybackUser;
    }

    public void setPaybackUser(String paybackUser) {
        this.paybackUser = paybackUser;
    }

    public Integer getConfirmedPaybackFee() {
        return confirmedPaybackFee;
    }

    public void setConfirmedPaybackFee(Integer confirmedPaybackFee) {
        this.confirmedPaybackFee = confirmedPaybackFee;
    }

    public Integer getPaybackFee() {
        return paybackFee;
    }

    public void setPaybackFee(Integer paybackFee) {
        this.paybackFee = paybackFee;
    }
}