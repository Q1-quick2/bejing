package com.sutong.systemManagement.service;

import com.github.pagehelper.PageInfo;
import com.sutong.systemManagement.model.entity.AuditSystemUserManage;

public interface SystemUserService {

    /**
     * 查询用户管理
     *
     * @param systemUserManage
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<AuditSystemUserManage> doFindUserManage(AuditSystemUserManage systemUserManage, int pageNum, int pageSize);

    /**
     * 创建用户管理
     *
     * @param systemUserManage
     * @param roleIds
     */
    void doInsertUserManage(AuditSystemUserManage systemUserManage, String[] roleIds);

    /**
     * 修改用户管理
     *
     * @param systemUserManage
     * @param roleIds
     */
    void doUpdateUserId(AuditSystemUserManage systemUserManage, String[] roleIds);

    /**
     * 删除用户管理
     *
     * @param userId
     */
    void doDeleteUserId(String userId);
}
