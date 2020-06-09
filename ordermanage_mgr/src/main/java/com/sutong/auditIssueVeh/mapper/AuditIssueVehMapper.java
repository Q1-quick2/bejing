package com.sutong.auditIssueVeh.mapper;

import com.sutong.bjstjh.entity.AuditIssueVeh;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
@Mapper
public interface AuditIssueVehMapper {
//    发行车辆信息新增插入
    Integer doInsertAuditIssueVeh(AuditIssueVeh auditIssueVeh);
//    发行车辆信息查询
    AuditIssueVeh doFindAuditIssueVeh(String ministryWorkOrderForeignId);

//    发行车辆信息删除
    void doDeleteAuditIssueVehById(String ministryWorkOrderForeignId);
}
