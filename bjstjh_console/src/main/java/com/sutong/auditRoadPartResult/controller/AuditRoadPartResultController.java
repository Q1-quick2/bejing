package com.sutong.auditRoadPartResult.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.auditEvidenceResult.model.AuditEvidenceResult;
import com.sutong.auditEvidenceResult.service.AuditEvidenceResultService;
import com.sutong.auditPublishResult.model.AuditPublishResult;
import com.sutong.auditPublishResult.service.AuditPublishResultService;
import com.sutong.auditRoadPartResult.model.AuditRoadPartResult;
import com.sutong.auditRoadPartResult.service.AuditRoadPartResultService;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.ObjectUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 路段稽核结论管理
 * @author： Mr.Kong
 * @date: 2019/12/16 19:18
 */
@CrossOrigin
@RestController
public class AuditRoadPartResultController {

    @Reference
    private AuditRoadPartResultService auditRoadPartResultService;
    @Reference
    private AuditPublishResultService auditPublishResultService;
    @Reference
    private AuditEvidenceResultService auditEvidenceResultService;

    // 绑定变量名字和属性，参数封装进类
    @InitBinder("roadPartAudit")
    public void initBinderAuditRoadPartResult(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("roadPartAudit.");
    }

    // 绑定变量名字和属性，参数封装进类
    @InitBinder("publicAudit")
    public void initBinderAuditPublishResult(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("publicAudit.");
    }


    /**
     * @description: 查询路段稽核结论详情
     * @auther: Mr.kong
     * @date: 2019/12/17 21:47
     * @Param orderId:工单编号
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/auditRoadPartResult/info")
    public Result doFindAuditPublishResultInfo(@RequestParam("orderId")String orderId ){
        Map<String,Object> resultMap=new HashMap<>();
        //路段稽核结论详情
        AuditRoadPartResult auditRoadPartResult = auditRoadPartResultService.doFindByOrderId(orderId);
        resultMap.put("auditRoadPartResult",auditRoadPartResult);
        //路段稽核结论证据
        AuditEvidenceResult evidenceResult=new AuditEvidenceResult();
        evidenceResult.setOrderId(auditRoadPartResult.getOrderId());
        evidenceResult.setAudType("2");//路段稽核结论证据
        List<AuditEvidenceResult> auditEvidenceResults = auditEvidenceResultService.doFindAuditEvidenceResultList(evidenceResult);
        resultMap.put("auditEvidenceResults",auditEvidenceResults);
        return Result.ok().data("resultMap",resultMap);
    }

    /**
     * @description: 发行、路段,稽核结论与证据展示
     * @auther: Mr.kong
     * @date: 2019/12/17 19:53
     * @Param vehicleId: 车牌号
     * @Param carColour: 车牌颜色
     * @Param roadPartAudit: 路段稽核结论实体
     * @Param pageNum: 当前页数
     * @Param pageSize: 分页条数
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/auditRoadPartResult/page")
    public Result doFindAuditMainResultPage(@ModelAttribute("publicAudit") AuditPublishResult publicAudit,
                                            @ModelAttribute("roadPartAudit") AuditRoadPartResult roadPartAudit,
                                            @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        Map<String,Object> resultMap=new HashMap<>();
        //发行方稽核结论
        AuditPublishResult auditPublishResult = auditPublishResultService.doFindByVehicleIdAndCarColour(publicAudit.getVehicleId(), publicAudit.getCarColour());
        //设置发行 证据数据量
        if (ObjectUtils.isNotNull(auditPublishResult)){
            AuditEvidenceResult auditEvidenceResult=new AuditEvidenceResult();
            auditEvidenceResult.setOrderId(auditPublishResult.getOrderId());
            auditEvidenceResult.setAudType("1");
            List<AuditEvidenceResult> auditEvidenceResults = auditEvidenceResultService.doFindAuditEvidenceResultList(auditEvidenceResult);
            auditPublishResult.setEvidenceTotal(auditEvidenceResults.size());
        }
        resultMap.put("auditPublishResult",auditPublishResult);
        //路段稽核结论
        PageInfo<AuditRoadPartResult> pageInfo=null;
        if(ObjectUtils.isNotNull(auditPublishResult)){
            roadPartAudit.setPublishOrderId(auditPublishResult.getOrderId());
            pageInfo = auditRoadPartResultService.doFindAuditRoadPartResultPage(pageNum, pageSize, roadPartAudit);
        }
        resultMap.put("pageInfo",pageInfo);
        return Result.ok().data("resultMap",resultMap);
    }
}
