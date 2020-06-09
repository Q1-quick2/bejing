package com.sutong.workorder.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditBlackListTempEntity
 * @Description: 黑名单表-中间表
 * @author: lichengquan
 * @date: 2020年01月06日 16:48
 * @Version: 1.0
 */
@Data
@ToString
@ApiModel
public class AuditBlackListTempEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 车辆车牌号码+颜色（格式为：车牌号码+间隔符+车牌颜色编码，间隔符：“_”；）
     */
    @ApiModelProperty(value = "车辆车牌号码+颜色")
    private String vehicleId;

    /**
     * 状态-1：进入黑名单 2：解除黑名单
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 进入黑名单原因（多个原因以“|”分割）
     */
    @ApiModelProperty(value = "进入黑名单原因")
    private String reason;

    /**
     * 生成时间
     */
    @ApiModelProperty(value = "生成时间")
    private String creationTime;

    /**
     * 黑名单类型-1：追缴黑名单
     */
    @ApiModelProperty(value = "黑名单类型")
    private Integer type;

    /**
     * 车脸特征版本号
     */
    @ApiModelProperty(value = "车辆特征版本号")
    private String vehicleFeatureVersion;

    /**
     * 车脸识别特征码
     */
    @ApiModelProperty(value = "车脸识别特征码")
    private String vehicleFeatureCode;

    /**
     * 欠费金额（单位：分）
     */
    @ApiModelProperty(value = "欠费金额")
    private Integer oweFee;

    /**
     * 欠费行为次数
     */
    @ApiModelProperty(value = "欠费行为次数")
    private Integer evasionCount;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private String version;
}
