package com.sutong.historyOrder.mapper;


import com.sutong.bjstjh.entity.AuditWorkOrderHistoryTable;
import com.sutong.bjstjh.entity.StatisticalInfo;
import com.sutong.historyOrder.model.ParameterHistoryOrder;
import com.sutong.workorder.model.AuditHistoryExcelDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Description: 历史工单常规外查工单管理
 */

@Repository
@Mapper
@Component
public interface HistoryOrderMapper {

    /**
     * 查询常规外查工单
     *
     * @param parameterHistoryOrder
     * @return
     */
    List<AuditWorkOrderHistoryTable> doFindOrder(ParameterHistoryOrder parameterHistoryOrder);

    /**
     * 创建常规外查工单
     *
     * @param auditWorkOrderHistoryTable
     * @return
     */

    Integer doInsertOrder(AuditWorkOrderHistoryTable auditWorkOrderHistoryTable);

    /**
     * 导入常规外查工单
     *
     * @param date
     */
    void doInsertTable(List<AuditWorkOrderHistoryTable> date);


    List<AuditWorkOrderHistoryTable> selectByInFormCustomersFlag();

    List<AuditWorkOrderHistoryTable> getCardLiftingWorkOrder();

    /**
     * 将提供id的客户修改为已通知
     *
     * @param pastWorkorderIds
     * @return
     */
    Integer updateInFormCustomersFlagById(List<String> pastWorkorderIds);

    Integer updateEtcStatus(List<String> pastWorkOrderIds);

    List<AuditWorkOrderHistoryTable> selectBatchById(List<String> pastWorkOrderIds);

    List<AuditWorkOrderHistoryTable> getAllList();


    /**
     * @description: 查询常规外查工单详情集合
     * @Param AuditWorkOrderHistoryTable: 常规外查工单实体
     * @return: AuditWorkOrderHistoryTable
     **/
    AuditWorkOrderHistoryTable doFindHistoryResultInfoSerialId(@Param("serialId") String serialId);



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
     * @param parameterHistoryOrder
     * @description: 查询追缴常规外查工单
     * @return: ParameterHistoryBuffer
     */

    List<AuditWorkOrderHistoryTable> doFindHistoryRecovered(ParameterHistoryOrder parameterHistoryOrder);

    /**
     * 常规外查工单责任归属统计
     * 现有的责任归属方(
     * 1-银行责任
     * 2-业务员责任
     * 3-系统责任
     * 4-客户责任
     * 5-其他)
     *
     * @return
     * @author pengwz
     * @date 2019年12月25日 下午5:02:25
     */
    List<StatisticalInfo> doFindHistoryTotal();

    /**
     * 常规外查工单导出查询
     *
     * @param auditHistoryExcelDTO
     * @return
     */
    List<AuditWorkOrderHistoryTable> doFindHistoryExcel(AuditHistoryExcelDTO auditHistoryExcelDTO);

    /**
     * 	根据主键(原表主键,非UUID生成的24位主键)批量查询常规外查工单信息
     * @param ids
     * @return 
     * @author pengwz
     * @date 2020年1月2日 上午10:48:58
     */
    List<AuditWorkOrderHistoryTable> doFindBatchById(List<Object> ids);
    
    /**
     * 	根据obuid批量查询历史工单信息
     * @param ids
     * @return 
     * @author pengwz
     * @date 2020年1月2日 上午10:48:58
     */
    List<AuditWorkOrderHistoryTable> doFindBatchByObuId(List<Object> ids);
}
