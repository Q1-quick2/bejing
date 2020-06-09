package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AuditHistoryExcelDTO
 * @Description: 省部级导出入参
 * @author: lichengquan
 * @date: 2020年01月02日 10:19
 * @Version: 1.0
 */
@Data
@ApiModel
public class AuditHistoryExcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 事件类别
     */
    @ApiModelProperty(value = "事件类别")
    private Integer eventTypeCode;

    /**
     * 责任归属
     */
    @ApiModelProperty(value = "责任归属")
    private Integer dutyBelong;

    /**
     * 标签号
     */
    @ApiModelProperty(value = "标签号")
    private String obuNo;

    /**
     * 车号
     */
    @ApiModelProperty(value = "车号")
    private String vehplateNo;

    /**
     * 车牌颜色
     */
    @ApiModelProperty(value = "车牌颜色")
    private Integer vehplateColorCode;

    /**
     * 区分省 1 ，部级 2 标签
     */
    @ApiModelProperty(value = "区分标签")
    private Integer differentFlag;

    /**
     * 变更状态（已变更"1"/未变更"0或空"）
     */
    @ApiModelProperty(value = "变更状态")
    private Integer changeStatus;

    /**
     * 补费状态（已补费"1"/未补费"0或空"）
     */
    @ApiModelProperty(value = "补费状态")
    private Integer status;

    /**
     * 解禁状态 （已解禁"1"/未解禁"0或空"）
     */
    @ApiModelProperty(value = "解禁状态")
    private Integer etcStatusList;

    /**
     * 发短信状态 （已发短信"1"/未发短信"0或空"）
     */
    @ApiModelProperty(value = "发短信状态")
    private Integer informCustomersFlag;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "发行营业厅")
    private String issuingAgent;

    @ApiModelProperty(value = "外查单号")
    private String externalOrderNo;

    @ApiModelProperty(value = "卡号")
    private String etcno;


}
