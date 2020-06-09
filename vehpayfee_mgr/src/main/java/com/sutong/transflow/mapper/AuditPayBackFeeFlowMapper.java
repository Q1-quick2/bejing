package com.sutong.transflow.mapper;


import com.sutong.transflow.model.AuditPayBackFeeFlow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
@Mapper
@Component
public interface AuditPayBackFeeFlowMapper {

    List<AuditPayBackFeeFlow> getByList(Map map);

    AuditPayBackFeeFlow getById(String flowId);

}