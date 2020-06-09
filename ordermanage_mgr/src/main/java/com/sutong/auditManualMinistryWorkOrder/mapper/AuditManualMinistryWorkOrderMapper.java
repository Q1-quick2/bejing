package com.sutong.auditManualMinistryWorkOrder.mapper;


import com.sutong.auditManualMinistryWorkOrder.model.AuditManualMinistryWorkOrderModel;
import com.sutong.bjstjh.entity.AuditManualMinistryWorkOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
@Mapper
public interface AuditManualMinistryWorkOrderMapper {
    //  部级工单创建保存
    Integer doInsertAuditManualMinistryWorkOrder(AuditManualMinistryWorkOrder auditManualMinistryWorkOrder);
    //    部级工单列表条件查询
    List<AuditManualMinistryWorkOrder> doFindAuditManualMinistryWorkOrderList(AuditManualMinistryWorkOrderModel auditManualMinistryWorkOrderModel);
    //    部级工单详情查询
    AuditManualMinistryWorkOrder doFindAuditManualMinistryWorkOrder(String ministryWorkOrderId);

}
