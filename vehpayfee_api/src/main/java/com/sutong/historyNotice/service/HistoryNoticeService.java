package com.sutong.historyNotice.service;

import com.github.pagehelper.PageInfo;
import com.sutong.dodgingtoll.model.AuditPastOrder;

import java.util.List;

public interface HistoryNoticeService {
    /*
     * @description: 新建客户历史逃费信息
     * @auther: Mr.Su
     * @date: 2019/12/18 15:03
     * @Param auditPastOrder: 客户历史逃费实体
     * @return: int
     **/
    int doInsertHistoryNoticeSelective(AuditPastOrder auditPastOrder);

    /*
     * @description: 新建客户历史逃费信息
     * @auther: Mr.Su
     * @date: 2019/12/18 15:05
     * @Param auditPastOrder: 客户历史逃费实体
     * @return: int
     **/
    int doInsertHistoryNotice(AuditPastOrder auditPastOrder);

    /* to
     * @description: 查询客户历史逃费通知列表
     * @auther: Mr.Su
     * @date: 2019/12/18 15:05
     * @Param auditPastOrder: 客户历史逃费实体
     * @return: java.util.List<com.sutong.dodgingtoll.model.AuditPastOrder>
     **/
    List<AuditPastOrder> doFindAuditPastOrderList(AuditPastOrder auditPastOrder);


    List<AuditPastOrder> doFindAuditPastOrderAutoList(AuditPastOrder auditPastOrder);

    PageInfo<AuditPastOrder> doFindAuditPastOrderPage(int pageNum, int pageSize, AuditPastOrder auditPastOrder);
}
