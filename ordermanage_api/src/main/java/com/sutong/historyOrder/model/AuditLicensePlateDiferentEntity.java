package com.sutong.historyOrder.model;

import com.sutong.bjstjh.annotation.Comment;
import com.sutong.bjstjh.annotation.Tables;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditLicensePlateDiferentEntity
 * @Description: 车牌不符表
 * @author: lichengquan
 * @date: 2019年12月25日 17:42
 * @Version: 1.0
 */
@Data
@ToString
@ApiModel
@Tables(vopri = "serialId")
public class AuditLicensePlateDiferentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private String serialId;

    /**
     * 视频日期
     */
    private String videoDate;

    /**
     * 反馈日期
     */
    private String feedbackDate;

    /**
     * 核查方(来文单位)
     */
    private String verificationParty;

    /**
     * 反馈批次
     */
    private String feedbackBatch;

    /**
     * 重复次数
     */
    private Integer repetitions;

    /**
     * 车号
     */
    private String vehplateno;

    /**
     * 判定/事件类别(字典)
     */
    private Integer determine;

    /**
     * 通知方式（字典）
     */
    private Integer notifictionMode;

    /**
     * 视频（视频里的车辆信息）
     */
    private String videoInformation;

    /**
     * 资料信息
     */
    private String dataInformation;

    /**
     * 客户电话
     */
    private String contactTelephoneno;

    /**
     * 禁用时间
     */
    private String disableDate;

    /**
     * 变更状态（已变更/未变更）
     */
    private Integer changeStatus;

    /**
     * 备用状态
     */
    private Integer status;

    /**
     * 删除标识
     */
    private Integer rmFlag;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 创建时间（事件发生时间）
     */
    private String createTime;

    /**
     * 经办人
     */
    private String chargePerson;

    /**
     * 经办人联系电话
     */
    private String chargePersonPhone;

    /**
     * 事件经过
     */
    private String eventPass;

    /**
     * 收费广场
     */
    @Comment("收费广场")
    private String tollPlaza;

    /**
     * 交易时间
     */
    @Comment("交易时间")
    private String transactionTime;

    /**
     * 实际车型
     */
    @Comment("实际车型")
    private String actualAehicleType;

    /**
     * 实际车牌号
     */
    @Comment("实际通行车辆")
    private String actualPlateNumber;

    /**
     * OBU车牌号
     */
    @Comment("OBU车牌号")
    private String obuPlateNumber;

    /**
     * OBU标签号
     */
    @Comment("OBU标签号")
    private String obuNumber;

    /**
     * ETC卡号
     */
    @Comment("IC卡号")
    private String etcNumber;

    /**
     * 解禁时间
     */
    private String liftingTime;

    // 证据(指向证据文件)
    private String proof;

    public AuditLicensePlateDiferentEntity() { }

    public AuditLicensePlateDiferentEntity(String serialId, String videoDate, String feedbackDate, String verificationParty, String feedbackBatch, Integer repetitions, String vehplateno, Integer determine, Integer notifictionMode, String videoInformation, String dataInformation, String contactTelephoneno, String disableDate, Integer changeStatus, Integer status, Integer rmFlag, String updateTime, String createTime, String chargePerson, String chargePersonPhone, String eventPass, String tollPlaza, String transactionTime, String actualAehicleType, String actualPlateNumber, String obuPlateNumber, String obuNumber, String etcNumber, String liftingTime, String proof) {
        this.serialId = serialId;
        this.videoDate = videoDate;
        this.feedbackDate = feedbackDate;
        this.verificationParty = verificationParty;
        this.feedbackBatch = feedbackBatch;
        this.repetitions = repetitions;
        this.vehplateno = vehplateno;
        this.determine = determine;
        this.notifictionMode = notifictionMode;
        this.videoInformation = videoInformation;
        this.dataInformation = dataInformation;
        this.contactTelephoneno = contactTelephoneno;
        this.disableDate = disableDate;
        this.changeStatus = changeStatus;
        this.status = status;
        this.rmFlag = rmFlag;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.chargePerson = chargePerson;
        this.chargePersonPhone = chargePersonPhone;
        this.eventPass = eventPass;
        this.tollPlaza = tollPlaza;
        this.transactionTime = transactionTime;
        this.actualAehicleType = actualAehicleType;
        this.actualPlateNumber = actualPlateNumber;
        this.obuPlateNumber = obuPlateNumber;
        this.obuNumber = obuNumber;
        this.etcNumber = etcNumber;
        this.liftingTime = liftingTime;
        this.proof = proof;
    }
}
