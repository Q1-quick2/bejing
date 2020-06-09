package com.sutong.dodgingtoll.mapper;



import com.sutong.dodgingtoll.model.AuditRoadPartResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
@Component
public interface AuditRoadPartResultMapper {
    List<Map<String,Object>> getRoadListByOid(String orderId);

    int insert(AuditRoadPartResult record);

    int insertSelective(AuditRoadPartResult record);

    AuditRoadPartResult selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(AuditRoadPartResult record);

    int updateByPrimaryKey(AuditRoadPartResult record);
}