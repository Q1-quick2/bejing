//package com.sutong.dodgingtoll.service.impl;
//
//import com.alibaba.dubbo.config.annotation.Service;
//import com.sutong.bjstjh.util.MapUtil;
//import com.sutong.dodgingtoll.mapper.AuditDissentEvidenceMapper;
//import com.sutong.dodgingtoll.mapper.AuditDissentFlowMapper;
//import com.sutong.dodgingtoll.model.AuditDissentEvidence;
//import com.sutong.dodgingtoll.model.AuditDissentFlow;
//import com.sutong.dodgingtoll.service.DissentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Map;
//import java.util.UUID;
//
///**
// * @Description:
// * @ClassName: DissentServiceImpl
// * @author： lzq
// * @date: 2019/12/25 20:15
// * @Version： 1.0
// */
//
//@Component
//@Service
//@Transactional
//public class DissentServiceImpl implements DissentService {
//
//    @Autowired
//    AuditDissentEvidenceMapper auditDissentEvidenceMapper;
//
//    @Autowired
//    AuditDissentFlowMapper auditDissentFlowMapper;
//
//    @Transactional
//    @Override
//    public void doInsertDissentFlow(Map<String,Object> map) {
//
//        AuditDissentFlow auditDissentFlow = MapUtil.map2Object(map, AuditDissentFlow.class);
//        AuditDissentEvidence auditDissentEvidence = MapUtil.map2Object(map, AuditDissentEvidence.class);
//
//        String id = UUID.randomUUID().toString().replaceAll("-", "");
//        auditDissentFlow.setDissentId(id);
//        auditDissentEvidence.setDissentId(id);
//        auditDissentEvidenceMapper.doInsertSelective(auditDissentEvidence);
//        auditDissentFlowMapper.doInsertSelective(auditDissentFlow);
//
//
//    }
//
//
//}
