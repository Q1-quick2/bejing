package com.sutong.workorder.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditGreyListTempEntity
 * @Description: 灰名单表-中间表
 * @author: lichengquan
 * @date: 2020年01月06日 17:38
 * @Version: 1.0
 */
@Data
@ToString
@ApiModel
public class AuditGreyListTempEntity implements Serializable {

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
     * 状态-1： 进入灰名单  2： 解除灰名单
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 进入灰名单原因（多个原因以“|”分割）
     * 1：疑似逃费车辆；
     * 2：稽查案底车辆（原黑名单车辆）；
     * 3：重点临界车型车辆；
     * 4：协查车辆；
     * 5：其他；
     */
    @ApiModelProperty(value = "进入黑名单原因")
    private String reason;

    /**
     * 生成时间
     */
    @ApiModelProperty(value = "生成时间")
    private String creationTime;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private String version;
}
