package com.sutong.auditPublishResult.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 发行稽核结论实体
 * @author： Mr.Kong
 * @date: 2019/12/16 16:36
 */
@Data
@ToString
public class AuditPublishResult implements Serializable {
    /**
     * 工单编号
     */
    private String orderId;

    /**
     * 车牌号
     */
    private String vehicleId;

    /**
     * 车牌颜色
     */
    private String carColour;

    /**
     * 车型
     */
    private String carType;

    /**
     * 发行方
     */
    private String issuing;

    /**
     * 类型
     */
    private String issuingType;

    /**
     * 编码
     */
    private String code;

    /**
     * 是否欠费
     */
    private String audIsLessFee;

    /**
     * 逃费类型
     */
    private String escapeType;

    /**
     * 责任主体
     */
    private String dutyBody;

    /**
     * 证据是否充足
     */
    private String evidenceEnough;

    /**
     * 垫付金额
     */
    private String advancesAmount;

    /**
     * 最后修改时间
     */
    private String lastUpdateTime;
    /**
     * 稽核时间
     */
    private String auditTime;
    /**
     * 稽核员
     */
    private String auditPeople;
    /**
     * 收费路径
     */
    private String chargeWay;
    /**
     * 途径单元
     */
    private String wayToUnit;
    /**
     * 应收金额
     */
    private String receivableAmount;

    /**
     * 实收金额
     */
    private String realAmount;

    /**
     * 欠费金额
     */
    private String oweAmount;

    /**
     * 其他金额
     */
    private String otherAmount;

    /**
     * 备注
     */
    private String remark;

    //=================================================
    /**
     * 证据数据量
     */
    private int evidenceTotal;
}
