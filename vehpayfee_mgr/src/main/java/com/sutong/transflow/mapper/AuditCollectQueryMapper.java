package com.sutong.transflow.mapper;



import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
@Component
public interface AuditCollectQueryMapper {


  List<Map<String,Object>> getMonthCount(Map map);
  List<Map<String,Object>> getYearCount(Map map);
}
