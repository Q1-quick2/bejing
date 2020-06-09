package com.sutong.dodgingtoll.mapper;


import com.sutong.dodgingtoll.model.AuditEvidenceResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
@Component
public interface AuditEvidenceResultMapper {

    List<AuditEvidenceResult> getAuditEvidenceListByOid(String orderId);

}