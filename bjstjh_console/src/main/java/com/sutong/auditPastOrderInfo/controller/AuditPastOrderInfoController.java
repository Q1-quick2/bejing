package com.sutong.auditPastOrderInfo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.auditPastOrderInfo.model.AuditPastOrderInfo;
import com.sutong.auditPastOrderInfo.service.AuditPastOrderInfoService;
import com.sutong.bjstjh.result.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 通行费差额明细单管理
 * @author： Mr.Kong
 * @date: 2019/12/18 10:09
 */
@CrossOrigin
@RestController
public class AuditPastOrderInfoController {

    @Reference
    private AuditPastOrderInfoService auditPastOrderInfoService;

    /**
     * @description: 查询通行费差额明细单分页
     * @auther: Mr.kong
     * @date: 2019/12/18 16:18
     * @Param transObuId: 交易时OBU号
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/auditPastOrderInfo/page")
    public Result doFindAuditPastOrderInfo(@RequestParam("transObuId")String transObuId,
                                           @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        AuditPastOrderInfo auditPastOrderInfo=new AuditPastOrderInfo();
        auditPastOrderInfo.setTransObuId(transObuId);
        PageInfo<AuditPastOrderInfo> pageInfo = auditPastOrderInfoService.doFindAuditPastOrderInfoPage(auditPastOrderInfo, pageNum, pageSize);
        return Result.ok().data("pageInfo",pageInfo);
    }
}
