package com.sutong.auditPublishResult.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.auditPublishResult.mapper.AuditPublishResultMapper;
import com.sutong.auditPublishResult.model.AuditPublishResult;
import com.sutong.auditPublishResult.service.AuditPublishResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 发行稽核结论管理
 * @author： Mr.Kong
 * @date: 2019/12/16 16:43
 */
@Component
@Service
public class AuditPublishResultServiceImpl implements AuditPublishResultService {

    @Autowired
    private AuditPublishResultMapper auditPublishResultMapper;

    @Override
    public AuditPublishResult doFindByVehicleIdAndCarColour(String vehicleId, String carColour) {
        AuditPublishResult auditPublishResult=new AuditPublishResult();
        auditPublishResult.setVehicleId(vehicleId);
        auditPublishResult.setCarColour(carColour);
        return auditPublishResultMapper.doFindByVehicleIdAndCarColour(auditPublishResult);
    }

    /**
     * @description: 查询发行稽核结论集合
     * @auther: Mr.kong
     * @date: 2019/12/17 15:25
     * @Param result: 发行稽核结论实体
     * @return: java.util.List<com.sutong.auditPublishResult.model.AuditPublishResult>
     **/
    @Override
    public List<AuditPublishResult> doFindAuditPublishResultList(AuditPublishResult result) {
        return auditPublishResultMapper.doFindAuditPublishResultList(result);
    }

    @Override
    public int deleteByPrimaryKey(String orderId) {
        return auditPublishResultMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public int insert(AuditPublishResult record) {
        return auditPublishResultMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditPublishResult record) {
        return auditPublishResultMapper.insertSelective(record);
    }

    @Override
    public AuditPublishResult doFindAuditPublishByOrderId(String orderId) {
        return auditPublishResultMapper.doFindAuditPublishByOrderId(orderId);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditPublishResult record) {
        return auditPublishResultMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuditPublishResult record) {
        return auditPublishResultMapper.updateByPrimaryKey(record);
    }
}
