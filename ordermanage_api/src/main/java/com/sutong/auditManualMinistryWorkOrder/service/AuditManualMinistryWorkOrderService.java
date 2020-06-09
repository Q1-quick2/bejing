package com.sutong.auditManualMinistryWorkOrder.service;

import com.github.pagehelper.PageInfo;
import com.sutong.auditManualMinistryWorkOrder.model.AuditManualMinistryWorkOrderModel;
import com.sutong.bjstjh.entity.AuditManualMinistryWorkOrder;

public interface AuditManualMinistryWorkOrderService {
//    部级工单创建插入
    Integer doInsertAuditManualMinistryWorkOrder(AuditManualMinistryWorkOrder auditManualMinistryWorkOrder);
//    部级工单条件分页查询
    PageInfo<AuditManualMinistryWorkOrder> doFindAuditManualMinistryWorkOrder(AuditManualMinistryWorkOrderModel auditManualMinistryWorkOrderModel);
//    部级工单详情查询
    AuditManualMinistryWorkOrder doFindAuditManualMinistryWorkOrder(String ministryWorkOrderId);
}
