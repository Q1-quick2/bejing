package com.sutong.systemManagement.model.entity;


import com.sutong.bjstjh.annotation.Tables;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: AuditSystemRoleManage
 * @Description: 系统管理角色表
 */


@Data
@ToString
@ApiModel
@Tables(vopri = "roleId")
public class AuditSystemRoleManage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键角色id
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 所属部门
     */
    private String subsidiaryOrgan;

    /**
     * 角色权限
     */
    private String rolePower;


}
