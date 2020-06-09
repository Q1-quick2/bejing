package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditRoadConclusionDTO
 * @Description: 路段稽核结论查询出参
 * @author: lichengquan
 * @date: 2020年01月15日 16:02
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditRoadConclusionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String checkCommentsId;

    /**
     * 所属表主表id
     */
    @ApiModelProperty(value = "所属主表id")
    private String workOrderId;

    /**
     * 路段
     */
    @ApiModelProperty(value = "路段")
    private Integer roadId;

    /**
     * 协查单位名称
     */
    @ApiModelProperty(value = "协查单位名称")
    private String unitName;

    /**
     * 确认逃费类型
     */
    @ApiModelProperty(value = "确认逃费类型")
    private String escapeType;

    /**
     * 是否欠费
     */
    @ApiModelProperty(value = "是否欠费")
    private Integer isLessFee;

    /**
     * 责任主体
     */
    @ApiModelProperty(value = "责任主体")
    private Integer reSponsor;

    /**
     * 证据是否充足
     */
    @ApiModelProperty(value = "证据是否充足")
    private Integer adequacy;

    /**
     * 收费单元数量
     */
    @ApiModelProperty(value = "收费单元数量")
    private Integer chargeUnitNumber;

    /**
     * 欠费金额(单位:分)
     */
    @ApiModelProperty(value = "欠费金额")
    private Integer fee;

    /**
     * 证据数量
     */
    @ApiModelProperty(value = "证据数量")
    private Integer amount;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private String updateTime;
}
