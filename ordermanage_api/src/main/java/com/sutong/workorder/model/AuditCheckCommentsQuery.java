package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditCheckCommentsQuery
 * @Description: 稽核结论列表主页面查询入参
 * @author: lichengquan
 * @date: 2020年01月07日 14:42
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditCheckCommentsQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 结论编码
     */
    @ApiModelProperty(value = "结论编码")
    private String code;

    /**
     * 车牌号码
     */
    @ApiModelProperty(value = "车牌号码")
    private String vehicleNo;

    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车辆颜色")
    private Integer vehColor;

    /**
     * 是否欠费
     */
    @ApiModelProperty(value = "是否欠费")
    private Integer isLessFee;

    /**
     * 责任主体
     */
    @ApiModelProperty(value = "责任主体")
    private Integer reSponsor;

    /**
     * 证据是否充足
     */
    @ApiModelProperty(value = "证据是否充足")
    private Integer adequacy;

    /**
     * 行径省份
     */
    @ApiModelProperty(value = "行径省份")
    private String passProvince;

    /**
     * 行径路段
     */
    @ApiModelProperty(value = "行径路段")
    private String passPath;

    /**
     * 时间范围-稽核时间-结论的创建时间(开始)
     */
    @ApiModelProperty(value = "稽核开始时间")
    private String auditStartTime;

    /**
     * 时间范围-稽核时间-结论的创建时间(结束)
     */
    @ApiModelProperty(value = "稽核开结束时间")
    private String auditEndTime;

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
