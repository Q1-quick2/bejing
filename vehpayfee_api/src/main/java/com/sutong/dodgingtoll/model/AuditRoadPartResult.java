package com.sutong.dodgingtoll.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * null
 * 
 * @author wcyong
 * 
 * @date 2019-12-28
 */
@Data
@ToString
public class AuditRoadPartResult  implements Serializable {
    /**
     * 工单编号
     */
    private String orderId;

    /**
     * 处理方省中心 Id
     */
    private String tollProvinceId;

    /**
     * 发行工单编号
     */
    private String publishOrderId;

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
     * 路段
     */
    private String audRoadId;

    /**
     * 协查单位
     */
    private String investigationUnit;

    /**
     * 确认逃费类型
     */
    private String escapeType;

    /**
     * 是否欠费
     */
    private String audIsLessFee;

    /**
     * 责任主体
     */
    private String dutyBody;

    /**
     * 证据是否充足
     */
    private String evidenceEnough;

    /**
     * 计费金额
     */
    private String billingAmount;

    /**
     * 修改时间
     */
    private String lastUpdateTime;

    /**
     * 收费单元编号
     */
    private String chargeUnitNumber;

    /**
     * 收费单元名称
     */
    private String chargeUnitName;

    /**
     * 通行时间
     */
    private String passTime;

    /**
     * 应收通行费
     */
    private String recePassAmount;

    /**
     * 拟合标记
     */
    private String fittingTags;

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

    /**
     * 应收金额
     */
    private String receivableAmount;

    /**
     * 途径单元
     */
    private String wayToUnit;


}