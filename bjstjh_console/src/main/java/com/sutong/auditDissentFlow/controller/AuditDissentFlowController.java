package com.sutong.auditDissentFlow.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.auditDissentEvidence.model.AuditDissentEvidence;
import com.sutong.auditDissentEvidence.service.AuditDissentEvidenceService;
import com.sutong.auditDissentFlow.model.AuditDissentFlow;
import com.sutong.auditDissentFlow.service.AuditDissentFlowService;
import com.sutong.bjstjh.result.Result;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 异议申请流水管理
 * @author： Mr.Kong
 * @date: 2019/12/19 15:20
 */
@CrossOrigin
@RestController
public class AuditDissentFlowController {

    // 绑定变量名字和属性，参数封装进类
    @InitBinder("auditDissentFlow")
    public void initBinderAuditDissentFlow(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("auditDissentFlow.");
    }

    @Reference
    private AuditDissentFlowService auditDissentFlowService;
    @Reference
    private AuditDissentEvidenceService auditDissentEvidenceService;


    /**
     * @description: 查询异议申请流水详情
     * @auther: Mr.kong
     * @date: 2019/12/19 16:32
     * @Param dissentId:异议流水ID
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/auditDissentFlow/info")
    public Result doFindAuditDissentFlowInfo(@RequestParam("dissentId")String dissentId){
        //异议申请流水详情
        AuditDissentFlow auditDissentFlow = auditDissentFlowService.selectByPrimaryKey(dissentId);
        //异议申请流水证据
        List<AuditDissentEvidence> auditDissentEvidenceList = auditDissentEvidenceService.doFindAuditDissentEvidenceList(auditDissentFlow.getDissentId());
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("auditDissentFlow",auditDissentFlow);
        resultMap.put("auditDissentEvidenceList",auditDissentEvidenceList);
        return Result.ok().data("resultMap",resultMap);
    }


    /**
     * @description: 查询异议申请流水分页
     * @auther: Mr.kong
     * @date: 2019/12/19 15:43
     * @Param auditDissentFlow: 异议申请流水实体
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/auditDissentFlow/page")
    public Result doFindAuditDissentFlowPage(@ModelAttribute("auditDissentFlow") AuditDissentFlow auditDissentFlow,
                                             @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        PageInfo<AuditDissentFlow> pageInfo = auditDissentFlowService.doFindAuditDissentFlowPage(pageNum, pageSize, auditDissentFlow);
        return Result.ok().data("pageInfo",pageInfo);
    }

}
