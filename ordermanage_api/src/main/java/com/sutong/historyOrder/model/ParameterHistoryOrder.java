package com.sutong.historyOrder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: ParameterHistoryOrder
 * @Description: 查询常规外查工单输入参数
 * @author:
 * @date: 2019年12月18日 11:32
 * @Version: 1.0
 */
@Data
@ApiModel
public class ParameterHistoryOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "事件类别")
    private String eventTypeCode;

    @ApiModelProperty(value = "责任归属")
    private String dutyBelong;

    @ApiModelProperty(value = "标签号")
    private String obuno;

    @ApiModelProperty(value = "车号")
    private String vehplateNo;

    @ApiModelProperty(value = "车牌颜色")
    private String vehplateColorCode;

    @ApiModelProperty(value = "缓冲期")
    private String buffer;

/*    @ApiModelProperty(value = "非缓冲期")
    private String unbuffer;*/

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    private Integer pageIndex;

    /*@ApiModelProperty(value = "当前页码")
    private Integer pageNumber;*/

    /**
     * 分页条数
     */
    @ApiModelProperty(value = "分页条数")
    private Integer pageSize;


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

    @ApiModelProperty(value = " 传入省级 == 1 传部级==2 有外查单号是省级无部级 ")
    private String externalOrderNo;

    @ApiModelProperty(value = "卡号")
    private String etcno;

}
