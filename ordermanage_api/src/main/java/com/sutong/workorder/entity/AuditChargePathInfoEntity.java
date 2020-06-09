package com.sutong.workorder.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditChargPathInfoEntity
 * @Description: 收费路径信息表
 * @author: lichengquan
 * @date: 2020年01月08日 17:31
 * @Version: 1.0
 */
@Data
@ToString
@ApiModel
public class AuditChargePathInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String keyId;

    /**
     * 稽核工单主键
     */
    private String workOrderId;

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
    private String passageTime;

    /**
     * 应收通行费（单位：分）
     */
    private Integer tollReceivable;

    /**
     * 拟合标记
     */
    private Integer fittingFlag;

    /**
     * 收费金额（单位：分）
     */
    private Integer chargeAmount;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 删除标识
     */
    private Integer rmFlag;
}
