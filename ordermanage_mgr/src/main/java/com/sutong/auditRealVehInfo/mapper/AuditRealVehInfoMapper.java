package com.sutong.auditRealVehInfo.mapper;

import com.sutong.bjstjh.entity.AuditRealVehInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
@Mapper
public interface AuditRealVehInfoMapper {
//    实际车辆信息插入
    Integer doInsertAuditRealVehInfo(AuditRealVehInfo auditRealVehInfo);
//    实际车辆信息详情查询
    AuditRealVehInfo doFindAuditRealVehInfo(String ministryWorkOrderForeignId);

//    实际车辆信息删除
    void doDeleteAuditRealVehInfoById(String realVehInfoId);
}
