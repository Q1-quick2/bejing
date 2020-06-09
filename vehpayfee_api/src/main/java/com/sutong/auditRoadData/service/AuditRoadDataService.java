package com.sutong.auditRoadData.service;

import com.sutong.auditRoadData.model.AuditRoadData;

/**
 * @Description: 路方数据管理
 * @author： Mr.Kong
 * @date: 2019/12/19 15:07
 */
public interface AuditRoadDataService {
    int deleteByPrimaryKey(String orderId);

    int insert(AuditRoadData record);

    int insertSelective(AuditRoadData record);

    AuditRoadData selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(AuditRoadData record);

    int updateByPrimaryKey(AuditRoadData record);
}
