package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditBlackListSelectQuery
 * @Description: 黑名单查询入参
 * @author: lichengquan
 * @date: 2019年12月18日 19:41
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditBlackListSelectQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 车牌号码
     */
    @ApiModelProperty(value = "车牌号码")
    private String vehPlateNo;

    /**
     * 车牌颜色(字典)
     */
    @ApiModelProperty(value = "车牌颜色")
    private Integer vehColor;

    /**
     * 黑名单原因（由两级构成，两级之间用“-”分割，多个原因以“|”分割，例如：1-2|3-6）
     */
    @ApiModelProperty(value = "黑名单原因")
    private String reason;

    /**
     * 黑名单状态(1 位数字
     * 1：进入黑名单
     * 2：解除黑名单 )
     */
    @ApiModelProperty(value = "黑名单状态")
    private Integer status;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Integer version;

    /**
     * 欠费金额(单位-分)下限
     */
    @ApiModelProperty(value = "欠费下限")
    private Integer lowerLimitOweFee;

    /**
     * 欠费金额(单位-分)上限
     */
    @ApiModelProperty(value = "欠费上限")
    private Integer upperLimitOweFee;

    /**
     * 生效开始时间
     */
    @ApiModelProperty(value = "生效开始时间")
    private String effectiveStartTime;

    /**
     * 生效结束时间
     */
    @ApiModelProperty(value = "生效结束时间")
    private String effectiveEndTime;

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
