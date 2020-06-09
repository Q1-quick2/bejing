package com.sutong.systemManagement.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.systemManagement.mapper.SystemRoleMapper;
import com.sutong.systemManagement.mapper.SystemUserMapper;
import com.sutong.systemManagement.model.entity.AuditSystemRoleManage;
import com.sutong.systemManagement.model.entity.AuditSystemUserManage;
import com.sutong.systemManagement.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private SystemRoleMapper systemRoleMapper;

    /**
     * 查询用户管理
     *
     * @param systemUserManage
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<AuditSystemUserManage> doFindUserManage(AuditSystemUserManage systemUserManage, int pageNum, int pageSize) {
        //根据条件查询用户
        PageHelper.startPage(pageNum, pageSize);
        List<AuditSystemUserManage> userManage = systemUserMapper.doFindUserManage(systemUserManage);
        //循环得到用户id,根据用户id查询对应的角色
        for (AuditSystemUserManage UserManages : userManage) {
            String userId = UserManages.getUserId();
            List<AuditSystemRoleManage> roleManage = systemRoleMapper.doFindRoleByUserId(userId);
            UserManages.setRoles(roleManage);
        }
        PageInfo<AuditSystemUserManage> pageInfo = new PageInfo<>(userManage);
        return pageInfo;

    }

    /**
     * 创建用户管理
     *
     * @param systemUserManage
     * @param roleIds
     */
    @Override
    public void doInsertUserManage(AuditSystemUserManage systemUserManage, String[] roleIds) {
        //创建用户信息
        systemUserMapper.doInsertUserManage(systemUserManage);
        //根据用户id循环创建对应的角色
        String userId = systemUserManage.getUserId();
        for (String roleId : roleIds) {
            systemUserMapper.doInsertUserRoleManage(userId, roleId);
        }
    }

    /**
     * 修改用户管理
     *
     * @param systemUserManage
     * @param roleIds
     */
    @Override
    public void doUpdateUserId(AuditSystemUserManage systemUserManage, String[] roleIds) {
        //修改用户信息
        systemUserMapper.doUpdateUserId(systemUserManage);
        //根据用户id修改角色
        String userId = systemUserManage.getUserId();
        //传入参数不为空先删除中间表数据然后重新添加角色,为空就是用户没选角色删除角色id
        if (roleIds != null) {
            systemUserMapper.doDeleteUserRoleId(userId);
            for (String roleId : roleIds) {
                //根据用户id循环创建对应的角色
                systemUserMapper.doInsertUserRoleManage(userId, roleId);
            }
        } else {
            systemUserMapper.doDeleteUserRoleId(userId);
        }

    }

    /**
     * 删除用户信息
     *
     * @param userId
     */
    @Override
    public void doDeleteUserId(String userId) {
        //删除用户
        systemUserMapper.doDeleteUserId(userId);
        //删除中间表对应角色id
        systemUserMapper.doDeleteUserRoleId(userId);

    }


}
