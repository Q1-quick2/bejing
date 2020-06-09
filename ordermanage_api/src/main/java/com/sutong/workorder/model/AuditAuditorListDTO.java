package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditAuditorListDTO
 * @Description: 创建工单出参
 * @author: lichengquan
 * @date: 2020年01月11日 16:42
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditAuditorListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String workOrderId;

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
     * 车种
     */
    @ApiModelProperty(value = "车种")
    private Integer vehClass;

    /**
     * 行径省份
     */
    @ApiModelProperty(value = "行径省份")
    private String passProvince;

    /**
     * 疑似逃费行为
     */
    @ApiModelProperty(value = "疑似逃费行为")
    private String escape;

    /**
     * 数据编号
     */
    @ApiModelProperty(value = "数据编号")
    private String dataNumber;

    /**
     * 工单标题
     */
    @ApiModelProperty(value = "工单标题")
    private String workOrderTitle;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 删除标识
     * 0:未删除 1:已删除
     */
    @ApiModelProperty(value = "删除标识")
    private Integer rmFlag;

}
