package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditBlackListSelectDTO
 * @Description: TODO
 * @author: lichengquan
 * @date: 2019年12月18日 20:08
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditBlackListSelectDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 车辆车牌号码
     */
    @ApiModelProperty(value = "车辆车牌号码")
    private String vehicleId;

    /**
     * 车辆颜色(字典)
     */
    @ApiModelProperty(value = "车辆颜色")
    private Integer vehicleColor;

    /**
     * 黑名单类型
     */
    @ApiModelProperty(value = "黑名单类型")
    private Integer type;

    /**
     * 黑名单状态(1 位数字
     * 1：进入黑名单
     * 2：解除黑名单 )
     */
    @ApiModelProperty(value = "黑名单状态")
    private Integer status;

    /**
     * 黑名单原因（由两级构成，两级之间用“-”分割，多个原因以“|”分割，例如：1-2|3-6）
     */
    @ApiModelProperty(value = "黑名单原因")
    private String reason;

    /**
     * 欠费金额(单位-元)
     */
    @ApiModelProperty(value = "欠费金额")
    private Integer oweFee;

    /**
     * 欠费行为次数（车辆欠费行为次数，已补费次数不计）
     */
    @ApiModelProperty(value = "欠费行为次数")
    private Integer evasioCount;

    /**
     * 黑名单生成时间（YYYY-MM-DDTHH:mm:ss）
     */
    @ApiModelProperty(value = "黑名单生成时间")
    private String creationTime;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private String version;
}
