package com.sutong.systemManagement.mapper;

import com.sutong.systemManagement.model.entity.AuditSystemRoleManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
@Component
public interface SystemRoleMapper {
    /**
     * 查询角色管理
     *
     * @param systemRoleManage
     * @return
     */
    List<AuditSystemRoleManage> doFindRoleManage(AuditSystemRoleManage systemRoleManage);

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
    AuditSystemRoleManage doFindRoleId(@Param("roleId") String roleId);

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
    Integer doDeleteRoleId(@Param("roleId") String roleId);

    /**
     * 根据用户id查询对应角色
     *
     * @param userId
     * @return
     */
    List<AuditSystemRoleManage> doFindRoleByUserId(@Param("userId") String userId);
}
