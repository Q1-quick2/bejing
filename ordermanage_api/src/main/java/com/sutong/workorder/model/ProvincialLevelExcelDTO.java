package com.sutong.workorder.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: ProvincialLevelExcelDTO
 * @Description: 省中心导出excel
 * @author: lichengquan
 * @date: 2019年12月25日 15:42
 * @Version: 1.0
 */
@Data
public class ProvincialLevelExcelDTO implements Serializable {

    /**
     * 序号
     */
    private String serialNumber;

    /**
     * 外查单号
     */
    private String externalOrderNo;

    /**
     * 核查方
     */
    private String verificationParty;

    /**
     * 发行营业厅
     */
    private String issuingAgent;

    /**
     * 签约时间
     */
    private String signDate;

    /**
     * 发行时间
     */
    private String issueTime;

    /**
     * 客编
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 车辆所有人
     */
    private String vehicleOwner;

    /**
     * 代办人
     */
    private String proxymercName;

    /**
     * 证件号码
     */
    private String certificateNumber;

    /**
     * 联系电话
     */
    private String contactTelePhoneNo;

    /**
     * 行驶证地址
     */
    private String drivingLicAddress;

    /**
     * 发行系统地址
     */
    private String drivingLicSystemAddress;

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
     * 逃费类型
     */
    private String evasionType;

    /**
     * ETC卡内车型
     */
    private String etcCarType;

    /**
     * ETC卡内车牌颜色
     */
    private String etcTruckColor;

    /**
     * 实际车型
     */
    private String carActualType;

    /**
     * 是否有客户资料
     */
    private String customerInfoFlag;

    /**
     * 分公司提交的证据（违规事件认定单和车道截图/车道视频）
     */
    private String proof;

    /**
     * 责任
     */
    private String dutyBelong;

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
     * 已追缴金额（元）
     */
    private Double alreadySumPays;

    /**
     * 未追缴金额（元）
     */
    private Double readySumPays;

    /**
     * 初次通行时间
     */
    private String firstPassTime;

    /**
     * 最后通行时间
     */
    private String lastPassTime;

    /**
     * 补缴时间
     */
    private String sumTime;

    /**
     * OBU禁用时间
     */
    private String obuDisableDate;

    /**
     * ETC卡禁用时间
     */
    private String etcDisableDate;

    /**
     * OBU解禁时间
     */
    private String obuLiftBanTime;

    /**
     * ETC解禁时间
     */
    private String etcLiftBanTime;

    /**
     * OBU状态
     */
    private String obuAccountStatus;

    /**
     * ETC卡状态
     */
    private String etcStatusList;

    /**
     * 车牌黑名单状态
     */
    private String vehStatusList;

    /**
     * 状态
     */
    private String status;

    /**
     * 责任
     */
    private String responsibilitys;

    /**
     * 备注
     */
    private String remark;

}
