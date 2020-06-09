package com.sutong.dodgingtoll.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class AuditPastOrderInfo implements Serializable{
    /**
     * 主键Id
     */
    private String id;

    /**
     * AUDIT_ PAST_ORDER表主键Id，一对多
     */
    private String pastOrderId;

    /**
     * 入口
     */
    private String enstation;

    /**
     * 出口
     */
    private String exstation;

    /**
     * 交易时车型
     */
    private String transVehicleType;

    /**
     * 办理时车型
     */
    private String doVehicleType;

    /**
     * 实际车型
     */
    private String vehicleType;

    /**
     * 交易时车牌号
     */
    private String transVehicleId;

    /**
     * 办理时车牌号
     */
    private String doVehicleId;

    /**
     * 交易时间
     */
    private String transSubtrFee;

    /**
     * 应扣费（单位：分）
     */
    private String oweFeeInfo;

    /**
     * 差额（单位：分）
     */
    private String subtrFee;

    private String transTime;

    /**
     * 交易时OBU号
     */
    private String transObuId;

    /**
     * 交易时卡号
     */
    private String transCardId;
    //缴费状态
    private String orderStatusInfo;

    private String vehicleColour;


}
