package com.sutong.dodgingtoll.mapper;


import com.sutong.dodgingtoll.model.AuditPastOrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
@Component
public interface AuditPastOrderInfoMapper {
    List<AuditPastOrderInfo> getPaetInfoListByObu(String obuId);

    int insert(AuditPastOrderInfo record);

    int insertSelective(AuditPastOrderInfo record);

    AuditPastOrderInfo getByPrimaryKey(String id);

    int doUpdateColorById(Map<String,Object> map);

    int updateByPrimaryKey(AuditPastOrderInfo record);
}