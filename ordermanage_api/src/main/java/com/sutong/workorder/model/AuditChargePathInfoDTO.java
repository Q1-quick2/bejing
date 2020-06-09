package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditChargePathInfoDTO
 * @Description: 收费路径信息表DTO
 * @author: lichengquan
 * @date: 2020年01月08日 19:58
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditChargePathInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String keyId;

    /**
     * 稽核工单主键
     */
    @ApiModelProperty(value = "稽核工单主键")
    private String workOrderId;

    /**
     * 收费单元编号
     */
    @ApiModelProperty(value = "收费单元编号")
    private String chargeUnitNumber;

    /**
     * 收费单元名称
     */
    @ApiModelProperty(value = "收费单元名称")
    private String chargeUnitName;

    /**
     * 通行时间
     */
    @ApiModelProperty(value = "通行时间")
    private String passageTime;

    /**
     * 应收通行费（单位：分）
     */
    @ApiModelProperty(value = "应收通行费")
    private Integer tollReceivable;

    /**
     * 拟合标记
     */
    @ApiModelProperty(value = "拟合标记")
    private Integer fittingFlag;

    /**
     * 收费金额（单位：分）
     */
    @ApiModelProperty(value = "收费金额")
    private Integer chargeAmount;
}
