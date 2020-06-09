package com.sutong.auditCheckResults.mapper;

import com.sutong.bjstjh.entity.AuditCheckResults;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Component
@Repository
public interface AuditCheckResultsMapper {
//    稽核结论对比结果新建插入
    Integer doInsertAuditCheckResults(AuditCheckResults auditCheckResults);
//    稽核结论对比结果查询
    AuditCheckResults doFindAuditCheckResults(String ministryWorkOrderForeignId);

//    稽核结论对比结果删除
    void doDeleteAuditCheckResultsById(String ministryWorkOrderForeignId);
}
