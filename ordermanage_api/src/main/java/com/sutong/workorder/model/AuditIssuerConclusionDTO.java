package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditIssuerConclusionDTO
 * @Description: 发行方稽核结论
 * @author: lichengquan
 * @date: 2020年01月15日 10:31
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditIssuerConclusionDTO implements Serializable {

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

}
