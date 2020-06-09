package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditGreyListSelectQuery
 * @Description: TODO
 * @author: lichengquan
 * @date: 2019年12月19日 16:22
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditGreyListSelectQuery implements Serializable {

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
     * 灰名单原因
     */
    @ApiModelProperty(value = "灰名单原因")
    private String reason;

    /**
     * 灰名单状态
     */
    @ApiModelProperty(value = "灰名单状态")
    private String status;

    /**
     * 生成起始时间
     */
    @ApiModelProperty(value = "生成起始时间")
    private String creationStartTime;

    /**
     * 生成结束时间
     */
    @ApiModelProperty(value = "生成结束时间")
    private String creationEndTime;

    /**
     * 录入身份id
     */
    @ApiModelProperty(value = "录入省份id")
    private Integer enteredProvinceId;

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
