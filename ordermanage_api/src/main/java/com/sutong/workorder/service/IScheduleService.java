package com.sutong.workorder.service;

import com.sutong.bjstjh.entity.AuditWorkOrderHistoryTable;

import java.util.List;

/**
 * @ClassName: IScheduleService
 * @Description: 工单相关定时任务
 * @author: lichengquan
 * @date: 2019年12月23日 15:12
 * @Version: 1.0
 */
public interface IScheduleService {

    /**
     * 稽核黑名单全量下载-保存定时任务
     */
    void blackListScheduleTask();

    /**
     * 稽核灰名单全量下载 - 保存定时任务
     */
    void greyListScheduleTask();

    /**
     * 取得历史工单表中未通知客户的工单
     *
     * @return
     */
    List<AuditWorkOrderHistoryTable> getAuditWorkOrderHistory();

    /**
     * 更改历史工单表中工单通知状态
     *
     * @param keyIds
     * @return
     */
    Integer updateInFormCustomersFlag(List<String> keyIds);

    /**
     * 取得未解禁的历史工单
     *
     * @return
     */
    List<AuditWorkOrderHistoryTable> getCardLiftingWorkOrder();

    /**
     * 更改历史工单表中的ETC卡状态
     *
     * @param keyIds
     * @return
     */
    Integer updateEtcStatus(List<String> keyIds);

}
