package com.sutong.historyNotice.mapper;

import com.sutong.dodgingtoll.model.AuditPastOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
@Component
public interface HistoryNoticeMapper {

    int doInsertHistoryNoticeSelective(AuditPastOrder auditPastOrder);

    int doInsertHistoryNotice(AuditPastOrder auditPastOrder);

    List<AuditPastOrder> doFindAuditPastOrderList(AuditPastOrder auditPastOrder);

    List<AuditPastOrder> doFindAuditPastOrderAutoList(AuditPastOrder auditPastOrder);

    List<AuditPastOrder> doFindAuditPastOrderPage(AuditPastOrder auditPastOrder);
}
