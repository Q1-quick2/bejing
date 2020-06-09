package com.sutong.dodgingtoll.mapper;


import com.sutong.dodgingtoll.model.AuditRunFeeFlow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
@Mapper
@Component
public interface AuditRunFeeFlowMapper {


    List<AuditRunFeeFlow> getRunFeeListByVid(Map<String,Object> map);
}