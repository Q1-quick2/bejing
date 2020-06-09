package com.sutong.dodgingtoll.mapper;


import com.sutong.dodgingtoll.model.AuditPublishResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
@Component
public interface AuditPublishResultMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(AuditPublishResult record);

    int insertSelective(AuditPublishResult record);

    AuditPublishResult selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(AuditPublishResult record);

    int updateByPrimaryKey(AuditPublishResult record);
}