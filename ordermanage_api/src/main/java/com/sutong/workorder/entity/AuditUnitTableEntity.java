package com.sutong.workorder.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditUnitTableEntity
 * @Description: 协查方实体
 * @author: lichengquan
 * @date: 2020年01月15日 19:32
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditUnitTableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "协查单位ID")
    private String unitId;

    /**
     * 该审核信息所属表（稽核结论）
     */
    @ApiModelProperty(value = "稽核结论")
    private String checkCommentsId;

    /**
     * 协查单位名称
     */
    @ApiModelProperty(value = "协查单位名称")
    private String unitName;

    /**
     * 稽核时间
     */
    @ApiModelProperty(value = "稽核时间")
    private String processTime;

    /**
     * 稽核员
     */
    @ApiModelProperty(value = "稽核员")
    private String processer;

    /**
     * 车型
     */
    @ApiModelProperty(value = "车型")
    private Integer vehicle;

    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车牌颜色")
    private Integer vehicleColor;

    /**
     * 车牌号码
     */
    @ApiModelProperty(value = "车牌号码")
    private String vehplateNo;

    /**
     * 确认逃费行为
     */
    @ApiModelProperty(value = "确认逃费行为")
    private Integer conFirmedeScape;

    /**
     * 是否逃费
     */
    @ApiModelProperty(value = "是否逃费")
    private String whetherFee;

    /**
     * 收费路径
     */
    @ApiModelProperty(value = "收费路径")
    private String tollPath;

    /**
     * 途径单元
     */
    @ApiModelProperty(value = "途径单元")
    private String pathwayUnit;

    /**
     * 应收金额
     */
    @ApiModelProperty(value = "应收金额")
    private Integer payFee;

    /**
     * 实收金额
     */
    @ApiModelProperty(value = "实收金额")
    private Integer tollFee;

    /**
     * 欠费金额
     */
    @ApiModelProperty(value = "欠费金额")
    private Integer fee;

    /**
     * 其他金额
     */
    @ApiModelProperty(value = "其他金额")
    private Integer otherFee;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 证据链
     */
    @ApiModelProperty(value = "证据链")
    private String chainOfEvidence;

}
