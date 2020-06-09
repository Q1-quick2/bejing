package com.sutong.transflow.service;

import java.util.List;
import java.util.Map;

public interface AuditCollectQueryService {

    List<Map<String,Object>> getYearCount( String payEndTime);
    List<Map<String,Object>> getMonthCount( String payEndTime);
    List<Map<String, Object>> sumTotalPay(List<Map<String, Object>> mapList);

   // PageInfo<Map<String, Object>> doFindPayEndInformModelPage(int pageNum, int pageSize, AuditCollectQuery auditCollectQuery);



}
