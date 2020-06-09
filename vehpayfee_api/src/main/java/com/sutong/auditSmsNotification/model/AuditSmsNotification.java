package com.sutong.auditSmsNotification.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description:
 * @author： Mr.Kong
 * @date: 2019/12/31 22:06
 */
@Data
@ToString
public class AuditSmsNotification implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String smsId;
    @ApiModelProperty(value = "发送短信类型  1 交易流水通知   2 解禁")
    private String smsType;
    @ApiModelProperty(value = "缴费人姓名")
    private String payerName;
    @ApiModelProperty(value = "缴费人联系电话")
    private String payerPhone;
    @ApiModelProperty(value = "缴费流水")
    private String payRecord;
    @ApiModelProperty(value = "缴费金额")
    private Integer payAmount;
    @ApiModelProperty(value = "缴费时间")
    private String payTime;
    @ApiModelProperty(value = "缴费成功与否")
    private String status;
    @ApiModelProperty(value = "缴费方式")
    private String payMode;
    @ApiModelProperty(value = "逃费时间")
    private String evasionTime;
    @ApiModelProperty(value = "逃费行为")
    private String evasionBehavior;
    @ApiModelProperty(value = "是否为历史, 1:非历史,2:历史")
    private String payHistory;
    @ApiModelProperty(value = "是否发送成功,1：成功，2：失败")
    private String sendSmsStatus;

    //===========================================
    @ApiModelProperty(value = "车牌号")
    private String vehicleNumber;
    @ApiModelProperty(value = "车牌颜色")
    private String vehicleColour;
    @ApiModelProperty(value = "OBU编号")
    private String obuId;

    @ApiModelProperty(value = "当前页码")
    private Integer pageNum;
    @ApiModelProperty(value = "分页条数")
    private Integer pageSize;


}
