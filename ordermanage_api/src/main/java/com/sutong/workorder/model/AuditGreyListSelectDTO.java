package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditGreyListSelectDTO
 * @Description: 灰名单查询DTO
 * @author: lichengquan
 * @date: 2019年12月19日 16:38
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditGreyListSelectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 录入省份名称
     */
    @ApiModelProperty(value = "录入省份名称")
    private String enteredProvince;

    /**
     * 车牌号码
     */
    @ApiModelProperty(value = "车牌号码")
    private String vehPlateNo;

    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车牌颜色")
    private Integer vehColor;

    /**
     * 灰名单状态
     */
    @ApiModelProperty(value = "灰名单状态")
    private Integer status;

    /**
     * 灰名单原因
     */
    @ApiModelProperty(value = "灰名单原因")
    private String reason;

    /**
     * 生成时间(YYYY-MM-DDTHH:mm:ss)
     */
    @ApiModelProperty(value = "生成时间")
    private String creationTime;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Integer version;

    /**
     * 证据链
     */
    @ApiModelProperty(value = "证据链")
    private String chainOfEvidence;

}
