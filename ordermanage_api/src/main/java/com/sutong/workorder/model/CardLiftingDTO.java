package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: CardLiftingDTO
 * @Description: 卡禁用接口出参
 * @author: lichengquan
 * @date: 2019年12月24日 14:20
 * @Version: 1.0
 */
@Data
@ApiModel
public class CardLiftingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     * 0代表成功；
     * 32260036未通过权限校验；
     * 1业务异常；
     * 3参数校验未通过；
     * 2调用接出服务异常
     */
    @ApiModelProperty(value = "返回码")
    private String RespCode;

    /**
     * 返回信息说明
     */
    @ApiModelProperty(value = "返回信息说明")
    private String RespMsg;

    /**
     * 返回结果
     */
    @ApiModelProperty(value = "返回结果")
    private Boolean RetValue;
}
