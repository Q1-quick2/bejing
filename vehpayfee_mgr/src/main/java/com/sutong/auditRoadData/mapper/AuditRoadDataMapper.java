package com.sutong.auditRoadData.mapper;

import com.sutong.auditRoadData.model.AuditRoadData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Description: 路方数据管理
 * @author： Mr.Kong
 * @date: 2019/12/19 15:03
 */

@Repository
@Component
@Mapper
public interface AuditRoadDataMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(AuditRoadData record);

    int insertSelective(AuditRoadData record);

    AuditRoadData selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(AuditRoadData record);

    int updateByPrimaryKey(AuditRoadData record);

}
