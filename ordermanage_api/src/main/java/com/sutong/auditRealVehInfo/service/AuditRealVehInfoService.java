package com.sutong.auditRealVehInfo.service;

import com.sutong.bjstjh.entity.AuditRealVehInfo;

public interface AuditRealVehInfoService {
//    实际车辆信息新增插入
    Integer doInsertAuditRealVehInfo (AuditRealVehInfo auditRealVehInfo);
//    实际车辆信息查询
    AuditRealVehInfo doFindAuditRealVehInfo(String ministryWorkOrderForeignId);
}
