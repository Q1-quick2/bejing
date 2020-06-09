package com.sutong.auditPublishResult.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.auditEvidenceResult.model.AuditEvidenceResult;
import com.sutong.auditEvidenceResult.service.AuditEvidenceResultService;
import com.sutong.auditPublishResult.model.AuditPublishResult;
import com.sutong.auditPublishResult.service.AuditPublishResultService;
import com.sutong.auditRoadPartResult.service.AuditRoadPartResultService;
import com.sutong.bjstjh.result.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 发行稽核结论管理
 * @author： Mr.Kong
 * @date: 2019/12/17 16:01
 */
@CrossOrigin
@RestController
public class AuditPublishResultController {

    @Reference
    private AuditRoadPartResultService auditRoadPartResultService;
    @Reference
    private AuditPublishResultService auditPublishResultService;
    @Reference
    private AuditEvidenceResultService auditEvidenceResultService;


    /**
     * @description: 查询发行稽核结论详情
     * @auther: Mr.kong
     * @date: 2019/12/17 16:09
     * @Param orderId:工单编号
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/auditPublishResult/info")
    public Result doFindAuditPublishResultInfo(@RequestParam("orderId")String orderId ){
        Map<String,Object> resultMap=new HashMap<>();
        //发行稽核结论详情
        AuditPublishResult auditPublishResult = auditPublishResultService.doFindAuditPublishByOrderId(orderId);
        resultMap.put("auditPublishResult",auditPublishResult);
        //发行稽核结论证据
        AuditEvidenceResult evidenceResult=new AuditEvidenceResult();
        evidenceResult.setOrderId(auditPublishResult.getOrderId());
        evidenceResult.setAudType("1");//发行稽核证据
        List<AuditEvidenceResult> auditEvidenceResults = auditEvidenceResultService.doFindAuditEvidenceResultList(evidenceResult);
        resultMap.put("auditEvidenceResults",auditEvidenceResults);
        return Result.ok().data("resultMap",resultMap);
    }
}
