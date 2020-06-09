package com.sutong.systemManagement.service;

import com.github.pagehelper.PageInfo;
import com.sutong.systemManagement.model.entity.AuditSystemRoleManage;

public interface SystemRoleService {
    /**
     * 查询角色管理
     *
     * @param systemRoleManage
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<AuditSystemRoleManage> doFindRoleManage(AuditSystemRoleManage systemRoleManage, int pageNum, int pageSize);

    /**
     * 创建角色管理
     *
     * @param systemRoleManage
     * @return
     */
    Integer doInsertRoleManage(AuditSystemRoleManage systemRoleManage);

    /**
     * 查询角色管理详情
     *
     * @param roleId
     * @return
     */
    AuditSystemRoleManage doFindRoleId(String roleId);

    /**
     * 修改角色管理
     *
     * @param systemRoleManage
     * @return
     */
    Integer doUpdateRoleId(AuditSystemRoleManage systemRoleManage);

    /**
     * 删除角色管理
     *
     * @param roleId
     * @return
     */
    Integer doDeleteRoleId(String roleId);

}
