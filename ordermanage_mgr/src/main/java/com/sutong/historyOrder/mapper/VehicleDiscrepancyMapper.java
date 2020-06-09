package com.sutong.historyOrder.mapper;

import com.sutong.historyOrder.model.AuditLicensePlateDiferentEntity;
import com.sutong.historyOrder.model.AuditLicensePlateQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
@Mapper
@Component
public interface VehicleDiscrepancyMapper {

    /**
     * 根据前台条件信息查询车牌不符工单表
     * @param auditLicensePlateQuery
     * @return
     */
    List<AuditLicensePlateDiferentEntity> doFindVehicle(AuditLicensePlateQuery auditLicensePlateQuery);

    /**
     * 创建车牌不符工单表
     *
     * @param auditLicensePlateDiferentEntity
     * @return
     */

    Integer doInsertVehicleDiscrepancy(AuditLicensePlateDiferentEntity auditLicensePlateDiferentEntity);


    /**
     * 依据id查询车牌不符工单详情
     * @param serialId
     * @return
     */
    AuditLicensePlateDiferentEntity doFindVehicleResultInfo(@Param("serialId")String serialId);

    /**
     * 车牌不符变更状态
     *
     * @param hashMap
     * @return
     */
    Integer doUpdate(Map hashMap);


    /**
     * 批量插入
     *
     * @param auditLicensePlateDiferentEntities
     * @return
     */
    Integer batchInsert(List<AuditLicensePlateDiferentEntity> auditLicensePlateDiferentEntities);

    /**
     * 依据入参查询
     *
     * @param auditLicensePlateQuery
     * @return
     */
    List<AuditLicensePlateDiferentEntity> selectByQuery(AuditLicensePlateQuery auditLicensePlateQuery);

    /**
     * 查询全部
     *
     * @return
     */
    List<AuditLicensePlateDiferentEntity> selectAll();

    /**
     * 依据id更新
     *
     * @param auditLicensePlateDiferentEntity
     * @return
     */
    Integer updateById(AuditLicensePlateDiferentEntity auditLicensePlateDiferentEntity);

    /**
     * 更新通知状态
     *
     * @param successIds
     * @return
     */
    Integer updateStatusFlagById(List<String> successIds);

    /**
     * 获得未解禁的车牌不符信息
     */
    List<AuditLicensePlateDiferentEntity> getNotLiftingEntity(@Param("status") String status);

    /**
     * 导入车牌不符工单表
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
    List<AuditLicensePlateDiferentEntity> doFindTableByETCAndTransactiontime(
    		@Param("transTimeList")List<Object> transTimeList,@Param("icCardList")List<Object> icCardList);
}


