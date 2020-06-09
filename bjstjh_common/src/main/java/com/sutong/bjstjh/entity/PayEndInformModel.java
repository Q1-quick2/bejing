package com.sutong.bjstjh.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by main on 2019/12/15.
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "支付返回结果")
public class PayEndInformModel implements Serializable{

    @ApiModelProperty(value = "字符集")
    private String  charset;
    @ApiModelProperty(value = "版本号")
    private String  version;
    @ApiModelProperty(value = "服务器证书")
    private String  serverCert;
    @ApiModelProperty(value = "服务器签名")
    private String  serverSign;
    @ApiModelProperty(value = "签名方式")
    private String  signType;
    @ApiModelProperty(value = "接口类型")
    private String  service;
    @ApiModelProperty(value = "商户编号")
    private String  merchantId;
    @ApiModelProperty(value = "支付结果状态")
    private String  resultCode;
    @ApiModelProperty(value = "支付状态描述")
    private String  resultMsg;
    @ApiModelProperty(value = "商户请求订单号")
    private String  paySerialNo;
    @ApiModelProperty(value = "交易金额")
    private Integer  txnAmt;
    @ApiModelProperty(value = "第三方支付订单号")
    private String  payOrderNo;
    @ApiModelProperty(value = "支付完成时间")
    private String  paymentTime;
    @ApiModelProperty(value = "支付类型")
    private String  payType;
    @ApiModelProperty(value = "备注")
    private String  rmk;
    @ApiModelProperty(value = "实际支付金额")
    private Integer  actPayAmt;
    @ApiModelProperty(value = "优惠金额")
    private Integer  discountsAmt;
    @ApiModelProperty(value = "优惠数量")
    private String  discountsInfoListNum;
    @ApiModelProperty(value = "优惠明细")
    private String  discountsInfoList;

    // 业务字段
    @ApiModelProperty(value = "是否历史缴费")
    private String history;






    // 返回字段
    //private String charset;// 字符集
    //private String version;// 接口版本
    //private String rmk;// 备注
    //private String signType;// 签名方式
    // private String service;// 业务类型
    @ApiModelProperty(value = "商户证书")
    private String merchantCert;
    @ApiModelProperty(value = "响应时间")
    private String requestTime;
    @ApiModelProperty(value = "商户签名")
    private String merchantSign;



}
