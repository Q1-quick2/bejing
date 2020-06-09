package com.sutong.auditMainResult.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.auditMainResult.model.AuditMainResult;
import com.sutong.auditMainResult.service.AuditMainResultService;
import com.sutong.bjstjh.result.Result;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 稽核结论信息管理
 * @author： Mr.Kong
 * @date: 2019/12/15 15:22
 */
@CrossOrigin
@RestController
public class AuditMainResultController {

    @Reference
    private AuditMainResultService auditMainResultService;

    // 绑定变量名字和属性，参数封装进类
    @InitBinder("auditMainResult")
    public void initBinderRunFee(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("auditMainResult.");
    }

    /**
     * @description: 查询稽核结论信息分页(稽核结论与证据展示)
     * @auther: Mr.kong
     * @date: 2019/12/15 15:42
     * @Param auditMainResult: 稽核结论信息实体
     * @Param pageNum: 当前页码
     * @Param pageSize:分页条数
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/auditMainResult/page")
    public Result doFindAuditMainResultPage(@ModelAttribute("auditMainResult") AuditMainResult auditMainResult,
                                   @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                   @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        //发行方稽核结论
        AuditMainResult mainResult=new AuditMainResult();
        mainResult.setProcessType("1");
        List<AuditMainResult> issuerAuditResult = auditMainResultService.doFindAuditMainResultList(mainResult);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("issuerAuditResult",issuerAuditResult);
        //路段稽核结论
        auditMainResult.setProcessType("2");
        PageInfo<AuditMainResult> pageInfo = auditMainResultService.doFindAuditMainResultPage(pageNum, pageSize, auditMainResult);
        resultMap.put("issuerAuditResult",issuerAuditResult);
        resultMap.put("pageInfo",pageInfo);
        return Result.ok().data("resultMap",resultMap);
    }
}
