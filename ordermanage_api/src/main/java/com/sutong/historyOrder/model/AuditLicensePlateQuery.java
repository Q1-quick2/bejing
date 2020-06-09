package com.sutong.historyOrder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditLicensePlateQuery
 * @Description: 车牌不符查询入参
 * @author: lichengquan
 * @date: 2019年12月26日 17:49
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditLicensePlateQuery implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 视频开始日期
     */
    @ApiModelProperty(value = "视频开始日期")
    private String videoDateStart;

    /**
     * 视频结束日期
     */

    @ApiModelProperty(value = "视频结束日期")
    private String videoDateEnd;

    /**
     * 核查方
     */
    @ApiModelProperty(value = "核查方")
    private String verificationParty;

    /**
     * 车号
     */
    @ApiModelProperty(value = "车号")
    private String vehplateno;

    /**
     * 判定/事件类别(字典)
     */
    @ApiModelProperty(value = "判定/事件类别(字典)")
    private Integer determine;

    /**
     * 变更状态（已变更/未变更）
     */
    @ApiModelProperty(value = "变更状态")
    private Integer changeStatus;

    /**
     * OBU车牌号
     */
    @ApiModelProperty(value = "OBU车牌号")
    private String obuPlateNumber;

    /**
     * OBU标签号
     */
    @ApiModelProperty(value = "OBU标签号")
    private String obuNumber;

    /**
     * ETC卡号
     */
    @ApiModelProperty(value = "ETC卡号")
    private String etcNumber;
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
