package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditPendDisposalDTO
 * @Description: 外部稽核-待处理查询-出参
 * @author: lichengquan
 * @date: 2020年01月13日 11:07
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditPendDisposalDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String workOrderId;

    /**
     * 工单标题
     */
    @ApiModelProperty(value = "工单标题")
    private String workOrderTitle;

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
     * 发起单位
     */
    @ApiModelProperty(value = "发起单位")
    private String workOrderUnit;

    /**
     * 发起时间
     */
    @ApiModelProperty(value = "发起时间")
    private String workOrderTime;

    /**
     * 协查单位进度
     */
    @ApiModelProperty(value = "协查单位进度")
    private String investigateProgress;

    /**
     * 数据稽核进度
     */
    @ApiModelProperty(value = "数据稽核进度")
    private String dataProgress;
}
