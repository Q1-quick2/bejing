package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: AuditConclusionDTO
 * @Description: 稽核结论-出参
 * @author: lichengquan
 * @date: 2020年01月08日 16:11
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditConclusionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "实际车牌")
    private String vehicleNo;

    @ApiModelProperty(value = "实际车牌颜色")
    private Integer vehColor;

    @ApiModelProperty(value = "实际车型")
    private Integer vehType;

    @ApiModelProperty(value = "实际车种")
    private Integer vehClass;

    @ApiModelProperty(value = "稽核时间")
    private String auditTime;

    @ApiModelProperty(value = "稽核员")
    private String processer;

    @ApiModelProperty(value = "确认逃费行为")
    private String escapeType;

    @ApiModelProperty(value = "是否逃费")
    private String whetherEscape;

    @ApiModelProperty(value = "证据链")
    private String proof;

    @ApiModelProperty(value = "收费路径")
    private List<AuditChargePathInfoDTO> auditChargePathInfos;

    @ApiModelProperty(value = "应收金额")
    private Integer amountReceivable;

    @ApiModelProperty(value = "实收金额")
    private Integer amountReceived;

    @ApiModelProperty(value = "欠费金额")
    private Integer amountOfArrears;

    @ApiModelProperty(value = "其他金额")
    private Integer otherAmount;


}
