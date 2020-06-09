package com.sutong.workorder.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditLogManagementEntity
 * @Description: 日志管理表Entity
 * @author: lichengquan
 * @date: 2019年12月18日 11:27
 * @Version: 1.0
 */
@Data
@ToString
@ApiModel
public class AuditLogManagementEntity implements Serializable {

    /**
     * 日志id
     */
    @ApiModelProperty(value = "日志id(非必填)")
    private String logId;
    /**
     * 系统方名称
     */
    @ApiModelProperty(value = "系统方名称(必填)")
    private String systemName;
    /**
     * 系统方编码
     */
    @ApiModelProperty(value = "系统方编码(必填)")
    private String systemCode;
    /**
     * 接口名称
     */
    @ApiModelProperty(value = "接口名称(必填)")
    private String interfaceName;
    /**
     * 请求参数信息
     */
    @ApiModelProperty(value = "请求参数信息(必填)")
    private String requestParameterInfo;
    /**
     * 返回参数信息
     */
    @ApiModelProperty(value = "返回参数信息(必填)")
    private String returnParameterInfo;
    /**
     * 删除标识(0:否，1:是)
     */
    @ApiModelProperty(value = "删除标识(非必填)")
    private Integer rmFlag;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间(非必填)")
    private String createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间(非必填)")
    private String updateTime;
}
