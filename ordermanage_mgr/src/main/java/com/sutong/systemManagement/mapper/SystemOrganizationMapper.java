package com.sutong.systemManagement.mapper;

import com.sutong.systemManagement.model.entity.AuditSystemOrganizationManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
@Component
public interface SystemOrganizationMapper {

    /**
     * 根据条件查询系统管理
     *
     * @param systemOrganizationManage
     * @return
     */
    List<AuditSystemOrganizationManage> doFindOrganizationManage(AuditSystemOrganizationManage systemOrganizationManage);

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
    AuditSystemOrganizationManage doFindOrganizationId(@Param("organizationId") String organizationId);

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
    Integer doDeleteOrganizationId(@Param("organizationId") String organizationId);


}
