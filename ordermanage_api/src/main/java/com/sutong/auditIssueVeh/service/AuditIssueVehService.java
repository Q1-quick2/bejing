package com.sutong.auditIssueVeh.service;

import com.sutong.bjstjh.entity.AuditIssueVeh;

public interface AuditIssueVehService {
//    发行车辆信息新增插入
    Integer doInsertAuditIssueVeh(AuditIssueVeh auditIssueVeh);
//    发行车辆信息查询
    AuditIssueVeh doFindAuditIssueVeh(String ministryWorkOrderForeignId);
}
