package com.sutong.historyOrder.service;

import com.github.pagehelper.PageInfo;
import com.sutong.historyOrder.model.AuditLicensePlateDiferentEntity;
import com.sutong.historyOrder.model.AuditLicensePlateQuery;

import java.util.List;
import java.util.Map;

public interface VehicleDiscrepancyService {

    /**
     * 根据前台条件信息查询车牌不符工单表
     *
     * @param auditLicensePlateQuery
     * @return
     */
    PageInfo<AuditLicensePlateDiferentEntity> doFindVehicle(AuditLicensePlateQuery auditLicensePlateQuery);

    /**
     * 创建车牌不符工单表
     *
     * @param
     * @return
     */
    Integer doInsertVehicleDiscrepancy(AuditLicensePlateDiferentEntity auditLicensePlateDiferentEntity);

    /**
     * @description:
     * @Param result: 车牌不符工单详情
     * @return: AuditLicensePlateDiferentEntity
     **/

    AuditLicensePlateDiferentEntity doFindVehicleResultInfo(String serialId);

    /**
     * 车牌不符变更状态
     *
     * @param hashMap
     * @return
     */
    Integer doUpdate(Map<String, Object> hashMap);

    /**
     * 更改车牌不符工单的通知状态
     */

    Integer doUpdateVehicle(List<String> successKeyIds);

    /**
     * 定时任务查询未解禁的信息
     */
    List<AuditLicensePlateDiferentEntity> getNotLiftingEntity();

    /**
     * 导入车牌不符工单表
     *
     * @param data
     */

    void doInsertVehicleTable(List<AuditLicensePlateDiferentEntity> data);

    /**
     * 更新车牌不符解禁状态
     */
    Integer doUpdateLiftingStatus(List<String> successKeyIds);

    /**
     * 	根据ETC卡号和交易时间批量查询车牌不符表
     * @param transTimeList
     * @param icCardList
     * @return 
     * @author pengwz
     * @date 2020年1月2日 下午4:48:19
     */
    List<AuditLicensePlateDiferentEntity> doFindTableByETCAndTransactiontime(List<Object> transTimeList,List<Object> icCardList);

}
