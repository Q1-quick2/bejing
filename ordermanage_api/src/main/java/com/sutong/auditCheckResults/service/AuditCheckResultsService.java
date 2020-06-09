package com.sutong.auditCheckResults.service;

import com.sutong.bjstjh.entity.AuditCheckResults;

public interface AuditCheckResultsService {
//    稽核结论对比结果新建插入
    Integer doInsertAuditCheckResults(AuditCheckResults auditCheckResults);
//    稽核结论对比结果查询
    AuditCheckResults doFindAuditCheckResults(String ministryWorkOrderForeignId);
//    稽核结论对比结果的图片路径查询
//    List doFindAuditCheckResultsImg(String ministryWorkOrderForeignId);
}
