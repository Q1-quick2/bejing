package com.sutong.bjstjh.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by main on 2019/12/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
public class UserModel implements Serializable{

    @ApiModelProperty(value = "商户订单号（主键ID）")
    private String orderNo;
    @ApiModelProperty(value = "用户id（客户号）")
    private String uid;
    @ApiModelProperty(value = "用户token")
    private String token;
    @ApiModelProperty(value = "用户名（收银台用户名）")
    private String userName;
    @ApiModelProperty(value = "用户手机号")
    private String phone;
    @ApiModelProperty(value = "用户真实姓名")
    private String pealName;
    @ApiModelProperty(value = "身份证号")
    private String idNumber;
    @ApiModelProperty(value = "总金额")
    private Integer money;











}
