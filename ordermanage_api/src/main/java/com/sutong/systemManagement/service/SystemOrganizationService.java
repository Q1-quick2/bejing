package com.sutong.systemManagement.service;


import com.github.pagehelper.PageInfo;
import com.sutong.systemManagement.model.entity.AuditSystemOrganizationManage;

/**
 * @author Administrator
 */
public interface SystemOrganizationService {
    /**
     * @param systemOrganizationManage
     * @param pageNum
     * @param pageSize
     * @return com.sutong.systemManagement.model.entity.AuditSystemOrganizationManage
     * 根据条件查询系统管理
     */
    PageInfo<AuditSystemOrganizationManage> doFindOrganizationManage(AuditSystemOrganizationManage systemOrganizationManage, int pageNum, int pageSize);

    /**
     * 创建机构管理
     *
     * @param systemOrganizationManage
     * @return
     */
    Integer doInsertOrganizationManage(AuditSystemOrganizationManage systemOrganizationManage);

    /**
     * 查询机构管理详情
     *
     * @param organizationId
     * @return
     */
    AuditSystemOrganizationManage doFindOrganizationId(String organizationId);

    /**
     * 修改机构管理
     *
     * @param systemOrganizationManage
     * @return
     */
    Integer doUpdateOrganizationId(AuditSystemOrganizationManage systemOrganizationManage);

    /**
     * 删除机构管理
     *
     * @param organizationId
     * @return
     */
    Integer doDeleteOrganizationId(String organizationId);
}
