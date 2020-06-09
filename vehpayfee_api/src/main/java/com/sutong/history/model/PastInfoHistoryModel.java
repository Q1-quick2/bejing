/**
 * @Description: 客户逃费实体
 * @ClassName: RunFee
 * @author： Mr.Kong
 * @date: 2019/12/13 15:13
 * @Version： 1.0
 */
package com.sutong.history.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PastInfoHistoryModel implements Serializable {
    /** ID*/
    private String id;
    /** AUDIT_ PAST_ORDER表主键Id，一对多*/
    private String pastOrderId;
    /** 入口*/
    private String enstation;
    /** 出口*/
    private String exstation;
    /** 交易时车型*/
    private String transVehicleType;
    /** 办理时车型*/
    private String doVehicleType;
    /** 实际车型*/
    private String vehicleType;
    /** 交易时车牌号*/
    private String transVehicleId;
    /** 办理时车牌号*/
    private String doVehicleId;
    /** 交易时扣费*/
    private String transSubtrFee;
    /** 应扣费*/
    private String oweFee;
    /** 差额*/
    private String subtrFee;
    /** 交易时间*/
    private String transTime;
    /** 交易时OBU号*/
    private String transObuId;
    /** 交易时卡号*/
    private String transCardId;
    /** 补费流水号*/
    private String payBackFeeId;
    /** 实缴金额*/
    private String payBackFeeReality;
    /** 工单状态*/
    private String orderStatus;

}
