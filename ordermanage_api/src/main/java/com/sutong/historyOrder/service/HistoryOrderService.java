package com.sutong.historyOrder.service;

import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.entity.AuditWorkOrderHistoryTable;
import com.sutong.historyOrder.model.ParameterHistoryOrder;

import java.util.List;
import java.util.Map;

public interface HistoryOrderService {

    /**
     * 查询常规外查工单信息
     *
     * @param parameterHistoryOrder
     * @return
     */
    PageInfo<AuditWorkOrderHistoryTable> doFindOrder(ParameterHistoryOrder parameterHistoryOrder);





    /**
     * 创建常规外查工单表
     *
     * @param auditWorkOrderHistoryTable
     * @return doInsertOrder
     */


    Integer doInsertOrder(AuditWorkOrderHistoryTable auditWorkOrderHistoryTable);

    /**
     * 导入常规外查工单表
     *
     * @param date
     * @return
     */
    void doInsertTable(List<AuditWorkOrderHistoryTable> date);

    /**
     * @description: 查询常规外查工单详情集合
     * @Param result: 常规外查工单实体
     * @return: AuditWorkOrderHistoryTable
     **/
    AuditWorkOrderHistoryTable doFindHistoryResultInfoSerialId(String serialId);


    /**
     * 修改常规外查补费状态
     *
     * @param hashMap
     * @return
     */
    Integer doUpdate1(Map hashMap);

    /**
     * 修改常规外查变更状态
     *
     * @param hashMap
     * @return
     */
    Integer doUpdate2(Map hashMap);


    /**
     * 查询常规外查追缴名单
     *
     * @param parameterHistoryBuffer
     * @return
     */

    PageInfo<AuditWorkOrderHistoryTable> doFindHistoryRecovered(ParameterHistoryOrder parameterHistoryBuffer);

    /**
     * 常规外查工单责任归属统计
     * 现有的责任归属方
     * 1-银行责任    bank
     * 2-业务员责任  salesman
     * 3-系统责任   system
     * 4-客户责任  client
     * 5-其他   other
     *
     * @return
     * @author pengwz
     * @date 2019年12月25日 下午5:02:25
     */
    Map<String, Integer> doFindHistoryTotal();
    /**
     * 	根据主键(原表主键,非UUID生成的24位主键)批量查询常规外查工单信息
     * @param id
     * @return
     * @author pengwz
     * @date 2020年1月2日 上午10:41:10
     */
    List<AuditWorkOrderHistoryTable> doFindBatchById(List<Object> id);

    /**
     * 	根据obu批量查询历史工单信息
     * @param id
     * @return
     * @author pengwz
     * @date 2020年1月2日 上午10:41:10
     */
    List<AuditWorkOrderHistoryTable> doFindBatchByObuId(List<Object> id);
}
