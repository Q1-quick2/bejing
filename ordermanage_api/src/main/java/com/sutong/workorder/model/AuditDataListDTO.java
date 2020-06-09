package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditDataListDTO
 * @Description: 外部稽核-稽核数据清单-出参
 * @author: lichengquan
 * @date: 2020年01月10日 10:08
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditDataListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 清单id
     */
    @ApiModelProperty(value = "清单id")
    private String workOrderId;

    /**
     * 车牌号码
     */
    @ApiModelProperty(value = "车辆标识-车牌号码")
    private String vehicleNo;

    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车辆标识-车牌颜色")
    private Integer vehColor;

    /**
     * 入口时间
     */
    @ApiModelProperty(value = "入口时间")
    private String entryTime;

    /**
     * 入口地点
     */
    @ApiModelProperty(value = "入口地点")
    private String entryPlace;

    /**
     * 出口时间
     */
    @ApiModelProperty(value = "出口时间")
    private String exitTime;

    /**
     * 出口地点
     */
    @ApiModelProperty(value = "出口地点")
    private String exitPlace;

    /**
     * 行径省份
     */
    @ApiModelProperty(value = "行径省份")
    private String passProvince;

    /**
     * 行径省份数
     */
    @ApiModelProperty(value = "行径省份数")
    private Integer passProvinceTotal;

    /**
     * 行径路段数
     */
    @ApiModelProperty(value = "行径路段数")
    private Integer passRoadTotal;

    /**
     * 通行介质类型
     */
    @ApiModelProperty(value = "通行介质类型")
    private Integer passMediaType;

    /**
     * OBU编码
     */
    @ApiModelProperty(value = "OBU编码")
    private String obuNo;

    /**
     * ETC/CPC编码
     */
    @ApiModelProperty(value = "ETC/CPC编码")
    private String etcNoCpcNo;

    /**
     * 特情类型
     */
    @ApiModelProperty(value = "特情类型")
    private Integer specialType;

    /**
     * 交易金额（单位：分）
     */
    @ApiModelProperty(value = "交易金额")
    private Integer ransTotal;

    /**
     * 是否发起稽核
     */
    @ApiModelProperty(value = "是否发起稽核")
    private Integer whetherAudit;

}
