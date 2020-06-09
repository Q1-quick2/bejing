package com.sutong.systemManagement.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.systemManagement.mapper.SystemOrganizationMapper;
import com.sutong.systemManagement.model.entity.AuditSystemOrganizationManage;
import com.sutong.systemManagement.service.SystemOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SystemOrganizationServiceImpl implements SystemOrganizationService {

    @Autowired
    private SystemOrganizationMapper systemOrganizationMapper;

    /**
     * @param systemOrganizationManage
     * @param pageNum
     * @param pageSize
     * @return 根据条件查询系统管理
     */
    @Override
    public PageInfo<AuditSystemOrganizationManage> doFindOrganizationManage(AuditSystemOrganizationManage systemOrganizationManage, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<AuditSystemOrganizationManage> OrganizationManage = systemOrganizationMapper.doFindOrganizationManage(systemOrganizationManage);
        PageInfo<AuditSystemOrganizationManage> pageInfo = new PageInfo<>(OrganizationManage);
        return pageInfo;
    }

    /**
     * 创建机构管理
     *
     * @param systemOrganizationManage
     * @return
     */
    @Override
    public Integer doInsertOrganizationManage(AuditSystemOrganizationManage systemOrganizationManage) {
        return systemOrganizationMapper.doInsertOrganizationManage(systemOrganizationManage);
    }

    /**
     * 查询机构管理详情
     *
     * @param organizationId
     * @return
     */
    @Override
    public AuditSystemOrganizationManage doFindOrganizationId(String organizationId) {
        return systemOrganizationMapper.doFindOrganizationId(organizationId);
    }

    /**
     * 修改机构管理
     *
     * @param systemOrganizationManage
     * @return
     */
    @Override
    public Integer doUpdateOrganizationId(AuditSystemOrganizationManage systemOrganizationManage) {
        return systemOrganizationMapper.doUpdateOrganizationId(systemOrganizationManage);

    }

    /**
     * 删除机构管理
     *
     * @param organizationId
     * @return
     */
    @Override
    public Integer doDeleteOrganizationId(String organizationId) {

        return systemOrganizationMapper.doDeleteOrganizationId(organizationId);
    }


}
