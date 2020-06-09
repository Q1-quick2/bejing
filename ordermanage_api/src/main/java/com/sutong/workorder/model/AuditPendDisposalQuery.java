package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditPendDisposalQuery
 * @Description: 外部稽核-待处理查询-入参
 * @author: lichengquan
 * @date: 2020年01月13日 13:55
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditPendDisposalQuery implements Serializable {

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
     * 工单标题
     */
    @ApiModelProperty(value ="工单标题")
    private String workOrderTitle;

    /**
     * 发起时开始时间
     */
    @ApiModelProperty(value = "发起开始时间")
    private String workOrderStartTime;

    /**
     * 发起结束时间
     */
    @ApiModelProperty(value = "发起结束时间")
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
