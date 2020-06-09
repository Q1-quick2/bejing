package com.sutong.auditPublishResult.mapper;

import com.sutong.auditPublishResult.model.AuditPublishResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 发行稽核结论管理
 * @author： Mr.Kong
 * @date: 2019/12/16 16:39
 */
@Repository
@Mapper
@Component
public interface AuditPublishResultMapper {

    AuditPublishResult doFindByVehicleIdAndCarColour(AuditPublishResult auditPublishResult);

    /**
     * @description: 查询发行稽核结论集合
     * @auther: Mr.kong
     * @date: 2019/12/17 15:25
     * @Param result: 发行稽核结论实体
     * @return: java.util.List<com.sutong.auditPublishResult.model.AuditPublishResult>
     **/
    List<AuditPublishResult> doFindAuditPublishResultList(AuditPublishResult result);

    int deleteByPrimaryKey(String orderId);

    int insert(AuditPublishResult record);

    int insertSelective(AuditPublishResult record);

    AuditPublishResult doFindAuditPublishByOrderId(String orderId);

    int updateByPrimaryKeySelective(AuditPublishResult record);

    int updateByPrimaryKey(AuditPublishResult record);
}
