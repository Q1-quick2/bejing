package com.sutong.systemManagement.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.systemManagement.mapper.SystemRoleMapper;
import com.sutong.systemManagement.model.entity.AuditSystemRoleManage;
import com.sutong.systemManagement.service.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    @Autowired
    private SystemRoleMapper systemRoleMapper;

    /**
     * 查询角色管理
     *
     * @param systemRoleManage
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<AuditSystemRoleManage> doFindRoleManage(AuditSystemRoleManage systemRoleManage, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<AuditSystemRoleManage> roleManage = systemRoleMapper.doFindRoleManage(systemRoleManage);
        PageInfo<AuditSystemRoleManage> pageInfo = new PageInfo<>(roleManage);
        return pageInfo;
    }

    /**
     * 创建角色管理
     *
     * @param systemRoleManage
     * @return
     */
    @Override
    public Integer doInsertRoleManage(AuditSystemRoleManage systemRoleManage) {
        return systemRoleMapper.doInsertRoleManage(systemRoleManage);

    }

    /**
     * 查询角色管理详情
     *
     * @param roleId
     * @return
     */
    @Override
    public AuditSystemRoleManage doFindRoleId(String roleId) {
        return systemRoleMapper.doFindRoleId(roleId);
    }

    /**
     * 修改角色管理
     *
     * @param systemRoleManage
     * @return
     */
    @Override
    public Integer doUpdateRoleId(AuditSystemRoleManage systemRoleManage) {
        return systemRoleMapper.doUpdateRoleId(systemRoleManage);
    }

    /**
     * 删除角色管理
     *
     * @param roleId
     * @return
     */
    @Override
    public Integer doDeleteRoleId(String roleId) {
        return systemRoleMapper.doDeleteRoleId(roleId);
    }

}
