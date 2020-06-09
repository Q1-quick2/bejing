package com.sutong.bjstjh.entity;

import java.io.Serializable;

/**
 * 客户补费凭证
 */
public class VoucherModel implements Serializable{

    private String orderId;             //  工单编码
    private String vehicleId;           //  车牌号
    private String vehicleColour;       //  车牌颜色
    private String payFeeId;            //  流水号
    private String paybackUser;         //  补费人姓名
    private String paybackUserPhone;    //  补费人联系方式
    private String paybackType;         //  补费方式
    private String payableAmount;       //  应缴金额
    private String paidinAmount;        //  实缴金额
    private String payableTime;         //  补费日期


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleColour() {
        return vehicleColour;
    }

    public void setVehicleColour(String vehicleColour) {
        this.vehicleColour = vehicleColour;
    }

    public String getPayFeeId() {
        return payFeeId;
    }

    public void setPayFeeId(String payFeeId) {
        this.payFeeId = payFeeId;
    }

    public String getPaybackUser() {
        return paybackUser;
    }

    public void setPaybackUser(String paybackUser) {
        this.paybackUser = paybackUser;
    }

    public String getPaybackUserPhone() {
        return paybackUserPhone;
    }

    public void setPaybackUserPhone(String paybackUserPhone) {
        this.paybackUserPhone = paybackUserPhone;
    }

    public String getPaybackType() {
        return paybackType;
    }

    public void setPaybackType(String paybackType) {
        this.paybackType = paybackType;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getPaidinAmount() {
        return paidinAmount;
    }

    public void setPaidinAmount(String paidinAmount) {
        this.paidinAmount = paidinAmount;
    }

    public String getPayableTime() {
        return payableTime;
    }

    public void setPayableTime(String payableTime) {
        this.payableTime = payableTime;
    }
}
