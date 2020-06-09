package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: CardliftingQuery
 * @Description: 卡解禁接口入参
 * @author: lichengquan
 * @date: 2019年12月24日 14:13
 * @Version: 1.0
 */
@Data
@ApiModel
public class CardLiftingQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端IP
     */
    @ApiModelProperty(value = "客户端IP")
    private String ClientIP;

    /**
     * 会话秘钥
     */
    @ApiModelProperty(value = "会话秘钥")
    private String SessionKey;

    /**
     * 卡号
     */
    @ApiModelProperty(value = "卡号")
    private String CardNo;

    /**
     * 是否验证特殊操作权限
     */
    @ApiModelProperty(value = "是否验证特殊操作权限")
    private Boolean isCheck;

}
