package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditRoadConclusionQuery
 * @Description: 路段稽核结论查询
 * @author: lichengquan
 * @date: 2020年01月15日 15:59
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditRoadConclusionQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 所属表主表id
     */
    @ApiModelProperty(value = "所属主表id")
    private String workOrderId;

    /**
     * 省份
     */
    @ApiModelProperty(value = "省份")
    private String province;

    /**
     * 路段
     */
    @ApiModelProperty(value = "路段")
    private Integer roadId;

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
     * 结论归属方 1:发行方 2：路方 3：总体
     */
    @ApiModelProperty(value = "结论归属方 1:发行方 2：路方 3：总体")
    private Integer adscription;
}
