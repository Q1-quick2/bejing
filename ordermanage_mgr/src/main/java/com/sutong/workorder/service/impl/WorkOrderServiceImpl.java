package com.sutong.workorder.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.historyOrder.mapper.HistoryOrderMapper;
import com.sutong.workorder.entity.AuditLogManagementEntity;
import com.sutong.workorder.mapper.AuditLogManagementResultMapper;
import com.sutong.workorder.model.LogManagementQuery;
import com.sutong.workorder.service.IWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName: WorkOrderServiceImpl
 * @Description: 日志管理实现
 * @author: lichengquan
 * @date: 2019年12月18日 11:36
 * @Version: 1.0
 */
@Service
public class WorkOrderServiceImpl implements IWorkOrderService {

    @Autowired
    private AuditLogManagementResultMapper auditLogManagementResultMapper;
    @Autowired
    private HistoryOrderMapper historyOrderMapper;

    /**
     * 保存日志信息
     *
     * @param
     * @return
     */
    @Override
    public Boolean doInsertLogManagement(AuditLogManagementEntity auditLogManagement) {
        auditLogManagement.setLogId(String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 24));
        int insert = auditLogManagementResultMapper.insert(auditLogManagement);
        if (insert == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 依据条件获取日志信息
     *
     * @param logManagementQuery
     * @return
     */
    @Override
    public PageInfo<AuditLogManagementEntity> doFindLogManagement(LogManagementQuery logManagementQuery) {
        PageHelper.startPage(logManagementQuery.getPageIndex(), logManagementQuery.getPageSize());
        List<AuditLogManagementEntity> auditLogManagementEntities = auditLogManagementResultMapper.selectByQuery(logManagementQuery);
        PageInfo<AuditLogManagementEntity> pageInfo = new PageInfo<>(auditLogManagementEntities);
        return pageInfo;
    }

    /**
     * 更新通知状态
     *
     * @param successKeyIds
     * @return
     */
    @Override
    public Integer doUpdateHistoryOrder(List<String> successKeyIds) {
        return historyOrderMapper.updateInFormCustomersFlagById(successKeyIds);
    }
}
