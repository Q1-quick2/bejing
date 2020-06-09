package com.sutong.bjstjh.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by main on 2019/12/22.
 */
@Data
@ToString
@ApiModel(value = "登录")
public class LoginModel implements Serializable{

    @ApiModelProperty(value = " 主键Id ")
    private String loginId;
    @ApiModelProperty(value = " 登录 账号")
    private String username;
    @ApiModelProperty(value = " 登录 密码")
    private String password;


}
