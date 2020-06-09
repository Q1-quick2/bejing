package com.sutong.bjstjh.entity.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by main on 2019/12/24.
 */
@Data
@ToString
@ApiModel(value = "AOP切面日志")
public class LogModel implements Serializable{

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "类名")
    private String className;
    @ApiModelProperty(value = "方法名")
    private String methodName;
    @ApiModelProperty(value = "请求参数")
    private String requesrParam;
    @ApiModelProperty(value = "返回参数")
    private String responseParam;
    @ApiModelProperty(value = "ip地址")
    private String ip;
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "日期")
    private String createTime;

}
