package com.sutong.workorder.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: MinistryLevelExcelDTO
 * @Description: 部中心导出excel
 * @author: lichengquan
 * @date: 2019年12月24日 19:33
 * @Version: 1.0
 */
@Data
public class MinistryLevelExcelDTO implements Serializable {
    /**
     * 序号
     */
    private String serialNumber;

    /**
     * 核查方
     */
    private String verificationParty;

    /**
     * 发行方
     */
    private String issuingPeople;

    /**
     * 发行方式
     */
    private String issuingType;

    /**
     * 发行机构
     */
    private String issuingAgent;

    /**
     * OBU号
     */
    private String obuno;

    /**
     * ETC卡号
     */
    private String etcno;

    /**
     * 车号
     */
    private String vehplateNo;

    /**
     * 是否逃费
     */
    private String whetherEvasion;

    /**
     * 未确认逃费原因
     */
    private String evasionReason;

    /**
     * 是否通知客户补缴
     */
    private String informCustomersFlag;

    /**
     * 是否下发状态名单
     */
    private String notificationBookFlag;

    /**
     * 逃费类型
     */
    private String evasionType;

    /**
     * ETC卡内车型
     */
    private String ectCarType;

    /**
     * 实际车型
     */
    private String carActualType;

    /**
     * 逃费通行次数
     */
    private String evasionCurrentNumber;

    /**
     * 核查省欠费金额（元）
     */
    private Double amountOfArrears;

    /**
     * 其他省欠费金额（元）
     */
    private Double otherAmountOfArrears;

    /**
     * 已追缴金额
     */
    private Double alreadySumPay;

    /**
     * 未追缴金额
     */
    private Double readySumPay;

    /**
     * 涉及通行省份（个）
     */
    private Integer provincesInvolved;

    /**
     * 涉及通行具体省份
     */
    private String specificProvincesInvolved;

    /**
     * 已追缴金额（元）
     */
    private Double alreadySumPays;

    /**
     * 未追缴金额（元）
     */
    private Double readySumPays;

    /**
     * 涉及通行省份（个）
     */
    private Integer provincesInvolveds;

    /**
     * 涉及通行具体省份
     */
    private String specificProvincesInvolveds;

    /**
     * 备注
     */
    private String remarks;


}
