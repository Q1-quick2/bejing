package com.sutong.systemManagement.mapper;


import com.sutong.systemManagement.model.entity.AuditSystemUserManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
@Component
public interface SystemUserMapper {

    /**
     * 根据条件查询用户
     *
     * @param systemUserManage
     * @return
     */
    List<AuditSystemUserManage> doFindUserManage(AuditSystemUserManage systemUserManage);

    /**
     * 创建用户信息
     *
     * @param systemUserManage
     */
    void doInsertUserManage(AuditSystemUserManage systemUserManage);

    /**
     * 根据用户id循环创建对应的角色
     *
     * @param userId
     * @param roleId
     */
    void doInsertUserRoleManage(@Param("userId") String userId, @Param("roleId") String roleId);

    /**
     * 修改用户信息
     *
     * @param systemUserManage
     */
    void doUpdateUserId(AuditSystemUserManage systemUserManage);

    /**
     * 删除用户
     *
     * @param userId
     */
    void doDeleteUserId(@Param("userId") String userId);

    /**
     * 删除中间表对应角色id
     *
     * @param userId
     */
    void doDeleteUserRoleId(@Param("userId") String userId);


}
