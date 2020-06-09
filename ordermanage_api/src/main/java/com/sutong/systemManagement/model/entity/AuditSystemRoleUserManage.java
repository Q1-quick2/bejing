package com.sutong.systemManagement.model.entity;


import com.sutong.bjstjh.annotation.Tables;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@ApiModel
@Tables(vopri = "userId")
public class AuditSystemRoleUserManage implements Serializable {

    private static final long serialVersionUID = 1L;

    /*  *//**
     * 主键中间表idCONNECTION_ID
     *//*
    private String connectionId;
*/
    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private String roleId;


}
