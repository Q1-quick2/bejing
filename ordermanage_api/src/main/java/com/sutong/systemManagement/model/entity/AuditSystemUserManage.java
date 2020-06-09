package com.sutong.systemManagement.model.entity;


import com.sutong.bjstjh.annotation.Tables;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: AuditSystemUserManage
 * @Description: 系统管理用户表
 */


@Data
@ToString
@ApiModel
@Tables(vopri = "userId")
public class AuditSystemUserManage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 手机
     */
    private String cellPhone;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生年月
     */
    private String birthday;

    /**
     * 身份证
     */
    private String idNumber;

    /**
     * 民族
     */
    private String nation;

    /**
     * 籍贯
     */
    private String nativel;

    /**
     * 邮箱
     */

    private String mail;


    /**
     * 当前用户具备哪些角色
     */
    private List<AuditSystemRoleManage> roles;
}