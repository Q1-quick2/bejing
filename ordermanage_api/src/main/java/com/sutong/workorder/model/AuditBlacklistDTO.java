package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditBlacklist
 * @Description: 稽核黑名单全量下载(下载到的数据传输实体)
 * @author: lichengquan
 * @date: 2019年12月18日 17:38
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditBlacklistDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 格式为：车牌号码+间隔符+车牌颜色编码，间隔符：“_”
     * 例：京 A12345_1
     */
    @ApiModelProperty(value = "车辆车牌号码+颜色")
    private String vehicleId;

    /**
     * 1位数字
     * 1：追缴黑名单
     */
    @ApiModelProperty(value = "黑名单类型")
    private Integer type;

    /**
     * 1 位数字
     * 1：进入黑名单
     * 2：解除黑名单
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 版	本	号（YYYYMMDD+2 位顺序码）+算法类型（2位数字）
     */
    @ApiModelProperty(value = "车脸识别版本号")
    private String vehicleFeatureVersion;

    /**
     * 车辆识别特征码
     */
    @ApiModelProperty(value = "车脸识别特征码")
    private String vehicleFeatureCode;

    /**
     * 由两级构成，两级之间用“-”分割，多个原因以“|”分割，具体编码参考：表十五，例如：1-2|3-6
     */
    @ApiModelProperty(value = "进行黑名单原因")
    private String reason;

    /**
     * 单位（分）
     */
    @ApiModelProperty(value = "欠费金额")
    private Integer oweFee;

    /**
     * 车辆欠费行为次数，已补费次数不计
     */
    @ApiModelProperty(value = "欠费行为次数")
    private Integer evasionCount;

    /**
     * YYYY-MM-DDTHH:mm:ss
     */
    @ApiModelProperty(value = "黑名单生成时间")
    private String creationTime;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private String version;

}
