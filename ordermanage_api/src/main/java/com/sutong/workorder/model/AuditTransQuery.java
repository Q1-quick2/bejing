package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditTransQuery
 * @Description: 门架数据查询-入参
 * @author: lichengquan
 * @date: 2020年01月14日 15:39
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditTransQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通行省份
     */
    @ApiModelProperty(value = "通行省份")
    private String prevailingProvinces;

    /**
     * 路段名称
     */
    @ApiModelProperty(value = "路段名称")
    private String roadName;

    /**
     * 交易开始时间
     */
    @ApiModelProperty(value = "交易开始时间")
    private String transStartTime;

    /**
     * 交易结束时间
     */
    @ApiModelProperty(value = "交易结束时间")
    private String transEndTime;

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
