package com.sutong.auditPastOrderInfo.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 通行费差额明细单实体
 * @author： Mr.Kong
 * @date: 2019/12/18 9:56
 */
@Data
@ToString
public class AuditPastOrderInfo implements Serializable {
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
     * 交易时扣费（单位：分）
     */
    private String transSubtrFee;

    /**
     * 应扣费（单位：分）
     */
    private String oweFee;

    /**
     * 差额（单位：分）
     */
    private String subtrFee;

    /**
     * 交易时间
     */
    private String transTime;

    /**
     * 交易时OBU号
     */
    private String transObuId;

    /**
     * 交易时卡号
     */
    private String transCardId;

    /**
     * 工单状态
     */
    private String orderStatus;

    /**
     * 补费流水号
     */
    private String payBackFeeId;

    /**
     * 实缴金额
     */
    private String payBackFeeReality;
}
