package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AduitSendsmsQuery
 * @Description: 发送短信入参
 * @author: lichengquan
 * @date: 2019年12月23日 11:55
 * @Version: 1.0
 */
@Data
@ApiModel
public class AduitSendsmsQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * keyId
     */
    @ApiModelProperty(value = "keyId")
    private String serialId;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String contactTelePhoneNo;
}
