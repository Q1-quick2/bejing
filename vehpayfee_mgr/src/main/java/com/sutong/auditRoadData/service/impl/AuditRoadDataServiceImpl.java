package com.sutong.auditRoadData.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.auditRoadData.mapper.AuditRoadDataMapper;
import com.sutong.auditRoadData.model.AuditRoadData;
import com.sutong.auditRoadData.service.AuditRoadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 路方数据管理
 * @author： Mr.Kong
 * @date: 2019/12/19 15:08
 */
@Component
@Service
public class AuditRoadDataServiceImpl implements AuditRoadDataService {

    @Autowired
    private AuditRoadDataMapper auditRoadDataMapper;

    @Override
    public int deleteByPrimaryKey(String orderId) {
        return auditRoadDataMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public int insert(AuditRoadData record) {
        return auditRoadDataMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditRoadData record) {
        return auditRoadDataMapper.insertSelective(record);
    }

    @Override
    public AuditRoadData selectByPrimaryKey(String orderId) {
        return auditRoadDataMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditRoadData record) {
        return auditRoadDataMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuditRoadData record) {
        return auditRoadDataMapper.updateByPrimaryKey(record);
    }
}
