package com.sutong.transflow.service;

import com.github.pagehelper.PageInfo;
import com.sutong.transflow.model.AuditPastOrderModel;
import com.sutong.transflow.model.AuditPayBackFeeFlow;

/**
* @description: 交易流水相关查询
* @auther: 李振全
* @date: 2019/12/22 11:01
* @Param null:
* @return: null
**/
public interface TransFlowService {

   public PageInfo<AuditPayBackFeeFlow> getTransFlowList(String vehicleId, String vehicleColour, Integer pageSize, Integer pageNum);

    public AuditPayBackFeeFlow getTransFlowInfo(String flowId);

    PageInfo<AuditPastOrderModel> getAuditPastOrderResultList(AuditPastOrderModel auditPastOrderModel,Integer pageNum,Integer pageSize);

    AuditPastOrderModel getAuditPastOrderResult(AuditPastOrderModel auditPastOrderModel);

}
