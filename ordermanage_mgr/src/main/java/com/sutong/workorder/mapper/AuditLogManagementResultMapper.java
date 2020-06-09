package com.sutong.workorder.mapper;

import com.sutong.workorder.entity.AuditLogManagementEntity;
import com.sutong.workorder.model.LogManagementQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AuditLogManagementResultMapper
 * @Description: 工单mapper接口
 * @author: lichengquan
 * @date: 2019年12月18日 11:38
 * @Version: 1.0
 */
@Repository
@Mapper
public interface AuditLogManagementResultMapper {

    /**
     * 插入日志
     *
     * @param auditLogManagement
     * @return
     */
    int insert(AuditLogManagementEntity auditLogManagement);

    /**
     * 依据条件查询日志
     *
     * @param logManagementQuery
     * @return
     */
    List<AuditLogManagementEntity> selectByQuery(LogManagementQuery logManagementQuery);
}
