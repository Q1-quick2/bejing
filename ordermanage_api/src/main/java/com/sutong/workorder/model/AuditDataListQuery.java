package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditDataListQuery
 * @Description: 外部稽核-稽核数据清单查询入参
 * @author: lichengquan
 * @date: 2020年01月10日 9:48
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditDataListQuery implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 是否已发起外部稽查
     */
    @ApiModelProperty(value = "是否已发起外部稽查")
    private Integer whetherAudit;

    /**
     * 行径省份
     */
    @ApiModelProperty(value = "行径省份")
    private String passProvince;

    /**
     * 行径路段
     */
    @ApiModelProperty(value = "行径路径")
    private String passPath;

    /**
     * 逃费类型（一类）
     */
    @ApiModelProperty(value = "疑似逃费类型-一类")
    private Integer escapeTypeOne;

    /**
     * 逃费类型(二类)
     */
    @ApiModelProperty(value = "疑似逃费行为-二类")
    private Integer escapeTypeTwo;

    /**
     * 时间类型
     */
    @ApiModelProperty(value = "时间类型")
    private String timeType;

    /**
     * 发起时间-时间范围（开始）
     */
    @ApiModelProperty(value = "时间范围-开始")
    private String workOrderStartTime;

    /**
     * 发起时间-时间范围-结束
     */
    @ApiModelProperty(value = "时间范围-结束")
    private String workOrderEndTime;

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    private Integer pageIndex;

    /**
     * 分页条数
     */
    @ApiModelProperty(value = "分页条数")
    private Integer pageSize;

}
