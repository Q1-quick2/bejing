package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditGreyListDTO
 * @Description: 稽核灰名单全量下载(下载到的数据传输实体)
 * @author: lichengquan
 * @date: 2019年12月18日 18:09
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditGreyListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 格式为：车牌号码+间隔符+车牌颜色编码，间隔符：“_”；
     * 例：京 A12345_1
     */
    @ApiModelProperty(value = "车辆车牌号码+颜色")
    private String vehicleId;

    /**
     * 1 位数字
     * 1： 进入灰名单
     * 2： 解除灰名单
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 多个原因以"|"分割
     * 1：疑似逃费车辆；
     * 2：稽查案底车辆（原黑名单车辆）；
     * 3：重点临界车型车辆；
     * 4：协查车辆；
     * 5：其他；
     */
    @ApiModelProperty(value = "进入灰名单原因")
    private String reason;

    /**
     * YYYY-MM-DDTHH:mm:ss
     */
    @ApiModelProperty(value = "灰名单生成时间")
    private String creationTime;

    /**
     * 省中心id
     */
    @ApiModelProperty(value = "省中心Id")
    private String tollProvinceId;

    /**
     * 省份
     */
    @ApiModelProperty(value = "省份")
    private String province;

    /**
     * 稽核灰名单全量版本号
     */
    @ApiModelProperty(value = "稽核灰名单全量版本号")
    private String version;
}
