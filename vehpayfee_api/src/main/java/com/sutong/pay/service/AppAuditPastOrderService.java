package com.sutong.pay.service;

import com.sutong.bjstjh.entity.AppAuditInvoiceInformationModel;
import com.sutong.bjstjh.entity.AppAuditPastOrderModel;

import java.util.List;
import java.util.Map;

public interface AppAuditPastOrderService {

    /**
     * 查询历史补费流水详情信息
     * @param auditPastOrderModel
     * @return
     */
    AppAuditPastOrderModel getAppAuditPastOrderInfoResult(AppAuditPastOrderModel auditPastOrderModel);
    /**
     * 查询历史补费流水列表集合
     */
    List<AppAuditPastOrderModel> getAppAuditPastOrderResultList(AppAuditPastOrderModel auditPastOrderModel);
    /**
     * 查询历史补费发票信息
     */
    AppAuditInvoiceInformationModel getAppAuditInvoiceInformationPastResult(AppAuditInvoiceInformationModel model);
    /**
     * 将历史补费发票信息存入数据库
     */
    int depositinappAuditInvoiceInformationPastService(Map<String,String> map);
}
