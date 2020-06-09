package com.sutong.workorder.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditCheckCommentsTableEntity
 * @Description: 稽核结论（稽核数据）
 * @author: lichengquan
 * @date: 2020年01月07日 13:53
 * @Version: 1.0
 */
@Data
@ToString
@ApiModel
public class AuditCheckCommentsTableEntity implements Serializable {

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
     * 结论归属方 1:发行方 2：路方 3：总体
     */
    @ApiModelProperty(value = "结论归属方 1:发行方 2：路方 3：总体")
    private Integer adscription;

    /**
     * 发行方
     */
    @ApiModelProperty(value = "发行方")
    private String issuer;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String type;

    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
    private String code;

    /**
     * 逃费类型
     */
    @ApiModelProperty(value = "逃费类型")
    private String escapeType;

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
     * 垫付金额（单位：分）
     */
    @ApiModelProperty(value = "垫付金额")
    private Integer advanceAmount;

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

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 省份
     */
    @ApiModelProperty(value = "省份")
    private String province;

    /**
     * 路段
     */
    @ApiModelProperty(value = "路段")
    private Integer roadId;

    /**
     * 是否欠费
     */
    @ApiModelProperty(value = "是否欠费")
    private Integer isLessFee;

    /**
     * 协查单位名称
     */
    @ApiModelProperty(value = "协查单位名称")
    private String unitName;

    /**
     * 协查单位id
     */
    @ApiModelProperty(value = "协查单位id")
    private String unitId;

    /**
     * 数据编号
     */
    @ApiModelProperty(value = "数据编号")
    private String dataNumber;

    /**
     * 欠费金额(单位:分)
     */
    @ApiModelProperty(value = "欠费金额")
    private Integer fee;

    /**
     * 行径省份
     */
    @ApiModelProperty(value = "行径省份")
    private String passProvince;

    /**
     * 疑似逃费行为
     */
    @ApiModelProperty(value = "疑似逃费行为")
    private String escape;

    /**
     * 数据处理进度
     */
    @ApiModelProperty(value = "数据处理进度")
    private String dataProgress;

    /**
     * 单位协查进度
     */
    @ApiModelProperty(value = "单位协查进度")
    private String unitProgress;

    /**
     * 稽核状态
     */
    @ApiModelProperty(value = "稽核状态")
    private Integer status;

    /**
     * 稽核人
     */
    @ApiModelProperty(value = "稽核人")
    private String processer;

    /**
     * 收费单元数量
     */
    @ApiModelProperty(value = "收费单元数量")
    private Integer chargeUnitNumber;
}
