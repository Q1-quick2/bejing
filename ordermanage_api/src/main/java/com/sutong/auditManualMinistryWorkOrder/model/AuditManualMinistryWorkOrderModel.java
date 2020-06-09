package com.sutong.auditManualMinistryWorkOrder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class AuditManualMinistryWorkOrderModel implements Serializable {
    //    车牌号码
    @ApiModelProperty(value = "车牌号码")
    private String vehPlateNo;
    //    车牌颜色编码
    @ApiModelProperty(value = "车牌颜色编码")
    private Integer vehColorCode;
    //    定义车型
    @ApiModelProperty(value = "定义车型")
    private Integer vehType;
    //    工单类型
    @ApiModelProperty(value = "工单类型")
    private Integer orderType;
    //    OBU编号
    @ApiModelProperty(value = "OBU编号")
    private String obuNo;
    //    ETC号
    @ApiModelProperty(value = "ETC号")
    private String etcNo;
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
