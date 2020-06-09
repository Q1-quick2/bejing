package com.sutong.historyOrder.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.historyOrder.mapper.VehicleDiscrepancyMapper;
import com.sutong.historyOrder.model.AuditLicensePlateDiferentEntity;
import com.sutong.historyOrder.model.AuditLicensePlateQuery;
import com.sutong.historyOrder.service.VehicleDiscrepancyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class VehicleDiscrepancyServiceImpl implements VehicleDiscrepancyService {

    @Autowired
    private VehicleDiscrepancyMapper vehicleDiscrepancyMapper;

    /**
     * 根据前台条件信息查询车牌不符工单表
     *
     * @param auditLicensePlateQuery
     * @return
     */
    @Override
    public PageInfo<AuditLicensePlateDiferentEntity> doFindVehicle(AuditLicensePlateQuery auditLicensePlateQuery) {
        //Mybatis分页插件PageHelper
        PageHelper.startPage(auditLicensePlateQuery.getPageIndex(), auditLicensePlateQuery.getPageSize());

        List<AuditLicensePlateDiferentEntity> auditLicensePlateDiferentEntities = vehicleDiscrepancyMapper.doFindVehicle(auditLicensePlateQuery);

        PageInfo<AuditLicensePlateDiferentEntity> pageInfo = new PageInfo<>(auditLicensePlateDiferentEntities);
        return pageInfo;

    }

    /**
     * 创建车牌不符表
     *
     * @param auditLicensePlateDiferentEntity
     * @return
     */
    @Override
    public Integer doInsertVehicleDiscrepancy(AuditLicensePlateDiferentEntity auditLicensePlateDiferentEntity) {
        return vehicleDiscrepancyMapper.doInsertVehicleDiscrepancy(auditLicensePlateDiferentEntity);

    }

    /**
     * 查询车牌不符工单详情
     *
     * @param serialId
     * @return
     */
    @Override
    public AuditLicensePlateDiferentEntity doFindVehicleResultInfo(String serialId) {

        AuditLicensePlateDiferentEntity auditLicensePlateDiferentEntity = vehicleDiscrepancyMapper.doFindVehicleResultInfo(serialId);
        return auditLicensePlateDiferentEntity;
    }

    /**
     * 车牌不符变更状态
     *
     * @param hashMap
     * @return
     */
    @Override
    public Integer doUpdate(Map<String, Object> hashMap) {
        Integer integer = vehicleDiscrepancyMapper.doUpdate(hashMap);
        return integer;

    }

    /**
     * 更改车牌不符工单的通知状态
     *
     * @param successKeyIds
     * @return
     */
    @Override
    public Integer doUpdateVehicle(List<String> successKeyIds) {
        return vehicleDiscrepancyMapper.updateStatusFlagById(successKeyIds);

    }

    /**
     * 车牌不符定时任务查询未通知的信息
     *
     * @return
     */
    @Override
    public List<AuditLicensePlateDiferentEntity> getNotLiftingEntity() {
        return vehicleDiscrepancyMapper.getNotLiftingEntity("1");
    }

    /**
     * 导入车牌不符工单表
     *
     * @param data
     */
    @Override
    public void doInsertVehicleTable(List<AuditLicensePlateDiferentEntity> data) {
        if (data != null && data.size() > 0) {

            vehicleDiscrepancyMapper.doInsertVehicleTable(data);
        }
    }


    @Override
    public Integer doUpdateLiftingStatus(List<String> successKeyIds) {
        return vehicleDiscrepancyMapper.doUpdateLiftingStatus(successKeyIds);
    }

    @Override
    public List<AuditLicensePlateDiferentEntity> doFindTableByETCAndTransactiontime(List<Object> transTimeList,
                                                                                    List<Object> icCardList) {
        return vehicleDiscrepancyMapper.doFindTableByETCAndTransactiontime(transTimeList, icCardList);
    }


}
