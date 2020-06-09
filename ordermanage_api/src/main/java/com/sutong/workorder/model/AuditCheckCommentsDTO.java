package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditCheckCommentsDTO
 * @Description: 稽核结论列表主页面查询出参
 * @author: lichengquan
 * @date: 2020年01月08日 10:27
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditCheckCommentsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 稽核结论主键
     */
    @ApiModelProperty(value = "稽核结论主键")
    private String checkCommentsId;

    /**
     * 结论编码
     */
    @ApiModelProperty(value = "结论编码")
    private String code;

    /**
     * 逃费类型
     */
    @ApiModelProperty(value = "逃费类型")
    private String escapeType;

    /**
     * 车牌号码
     */
    @ApiModelProperty(value = "车牌号码")
    private String vehicleNo;

    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车牌颜色")
    private Integer vehColor;

    /**
     * 车型
     */
    @ApiModelProperty(value = "车型")
    private Integer vehType;

    /**
     * 车种
     */
    @ApiModelProperty(value = "车种")
    private Integer vehClass;

    /**
     * 行径路段
     */
    @ApiModelProperty(value = "路段")
    private String passPath;

    /**
     * 稽核时间
     */
    @ApiModelProperty(value = "稽核时间")
    private String auditTime;

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
     * 欠费金额(单位:分)-计费金额
     */
    @ApiModelProperty(value = "欠费金额-计费金额")
    private Integer fee;

    /**
     * 证据数量
     */
    @ApiModelProperty(value = "证据数量")
    private Integer amount;


}
