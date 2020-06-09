package com.sutong.workorder.service;

import com.github.pagehelper.PageInfo;
import com.sutong.workorder.entity.AuditLogManagementEntity;
import com.sutong.workorder.model.LogManagementQuery;

import java.util.List;

/**
 * @ClassName: IWorkOrderService
 * @Description: 工单相关service接口
 * @author: lichengquan
 * @date: 2019年12月18日 11:34
 * @Version: 1.0
 */
public interface IWorkOrderService {

    /**
     * 保存日志信息
     *
     * @param auditLogManagement
     * @return
     */
    Boolean doInsertLogManagement(AuditLogManagementEntity auditLogManagement);

    /**
     * 依据条件获取日志信息
     *
     * @param logManagementQuery
     * @return
     */
    PageInfo<AuditLogManagementEntity> doFindLogManagement(LogManagementQuery logManagementQuery);

    /**
     * 更改历史工单的通知状态
     */
    Integer doUpdateHistoryOrder(List<String> successKeyIds);
}
