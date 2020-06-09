package com.sutong.dodgingtoll.mapper;


import com.sutong.dodgingtoll.model.AuditPastOrder;
import com.sutong.dodgingtoll.model.vo.AuditPastOrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
@Mapper
@Component
public interface AuditPastOrderMapper {
    int deleteByPrimaryKey(String pastOrderId);

    int insert(AuditPastOrder record);

    int insertSelective(AuditPastOrder record);

    AuditPastOrder getOrderPastByObId(String obuId);

    int updateByPrimaryKeySelective(AuditPastOrder record);

    int getCountByObu(String obuId);
    List<AuditPastOrderVo> getByObuId(Map map);
}