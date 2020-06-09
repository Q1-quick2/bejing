package com.sutong.workorder.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditBlackListTableEntity
 * @Description: 黑名单实体类
 * @author: lichengquan
 * @date: 2019年12月18日 18:23
 * @Version: 1.0
 */
@Data
@ToString
@ApiModel
public class AuditBlackListTableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String blackListId;

    /**
     * 车牌号码
     */
    @ApiModelProperty(value = "车牌号码")
    private String vehPlateNo;

    /**
     * 车牌颜色(字典)
     */
    @ApiModelProperty(value = "车牌颜色")
    private Integer vehColor;

    /**
     * 黑名单原因（由两级构成，两级之间用“-”分割，多个原因以“|”分割，例如：1-2|3-6）
     */
    @ApiModelProperty(value = "黑名单原因")
    private String reason;

    /**
     * 黑名单状态(1 位数字
     * 1：进入黑名单
     * 2：解除黑名单 )
     */
    @ApiModelProperty(value = "黑名单状态")
    private Integer status;

    /**
     * 黑名单类型
     */
    @ApiModelProperty(value = "黑名单类型")
    private Integer type;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private String version;

    /**
     * 黑名单生成时间（YYYY-MM-DDTHH:mm:ss）
     */
    @ApiModelProperty(value = "黑名单生成时间")
    private String creationTime;

    /**
     * 生效时间
     */
    @ApiModelProperty(value = "生效时间")
    private String effectiveTime;

    /**
     * 欠费金额(单位-分)
     */
    @ApiModelProperty(value = "欠费金额")
    private String oweFee;

    /**
     * 欠费行为次数（车辆欠费行为次数，已补费次数不计）
     */
    @ApiModelProperty(value = "欠费行为次数")
    private Integer evasioCount;

    /**
     * 车脸特征版本号（版 本 号（YYYYMMDD+2 位
     * 顺序码）+算法类型（2位数字））
     */
    @ApiModelProperty(value = "车脸特征版本号")
    private String vehicleFeatureVersion;

    /**
     * 车脸识别特征码
     */
    @ApiModelProperty(value = "车脸识别特征码")
    private String vehicleFeatureCode;

    /**
     * 证据
     */
    @ApiModelProperty(value = "证据")
    private String proof;

    /**
     * 删除标识(0:否，1:是)
     */
    @ApiModelProperty(value = "删除标识")
    private Integer rmFlag;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
}
