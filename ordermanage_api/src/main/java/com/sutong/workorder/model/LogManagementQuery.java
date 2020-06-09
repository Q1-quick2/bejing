package com.sutong.workorder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: LogManagementQuery
 * @Description: 获取日志输入参数
 * @author: lichengquan
 * @date: 2019年12月18日 11:32
 * @Version: 1.0
 */
@Data
@ApiModel
public class LogManagementQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统方名称")
    private String systemName;
    @ApiModelProperty(value = "开始时间")
    private String createStartTime;
    @ApiModelProperty(value = "结束时间")
    private String createEndTime;
    @ApiModelProperty(value = "接口名称")
    private String interfaceName;
    @ApiModelProperty(value = "当前页码")
    private Integer pageIndex;
    @ApiModelProperty(value = "分页条数")
    private Integer pageSize;
}
