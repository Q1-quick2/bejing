package com.sutong.transflow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.util.ObjectUtils;
import com.sutong.transflow.mapper.AuditCollectQueryMapper;
import com.sutong.transflow.service.AuditCollectQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AuditCollectQueryServiceImpl
 * @Description
 * @Author: Mr.Su
 * @Date: 2020/1/6 10:26
 * @Version V1.0
 **/
@Component
@Service
public class AuditCollectQueryServiceImpl implements AuditCollectQueryService {

    @Autowired
   private AuditCollectQueryMapper auditCollectQueryMapper;


    @Override
    public List<Map<String, Object>> getYearCount(String payEndTime) {
        HashMap<String, Object> map=null;
        if(null!=payEndTime&&!"".equals(payEndTime)) {
            map = new HashMap<>();
            map.put("payEndTime", payEndTime);
        }

        List<Map<String, Object>> listMaps = auditCollectQueryMapper.getYearCount(map);

        return listMaps;
    }

    @Override
    public List<Map<String, Object>> getMonthCount(String payEndTime) {
        HashMap<String, Object> map=null;
        if(null!=payEndTime&&!"".equals(payEndTime)) {
            map = new HashMap<>();
            map.put("payEndTime", payEndTime);
        }

        List<Map<String, Object>> listMaps = auditCollectQueryMapper.getMonthCount(map);

        return listMaps;
    }

    public List<Map<String, Object>> sumTotalPay(List<Map<String, Object>> mapList){
        if (ObjectUtils.isNotNull(mapList)){
            for (Map map: mapList){
                long wxPay = (long)map.get("WXPAY");
                long aliPay = (long)map.get("ALIPAY");
                long upoPay = (long)map.get("UPOPPAY");
                long totalPay=wxPay+aliPay+upoPay;
                map.put("totalPay",totalPay);
            }
        }
        return mapList;
    }
}
