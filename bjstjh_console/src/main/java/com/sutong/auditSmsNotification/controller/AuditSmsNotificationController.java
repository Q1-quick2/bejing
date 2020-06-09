package com.sutong.auditSmsNotification.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.auditSmsNotification.model.AuditSmsNotification;
import com.sutong.auditSmsNotification.service.AuditSmsNotificationService;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author： Mr.Kong
 * @date: 2019/12/31 22:12
 */
@Api(tags = "补费通知列表-接口管理")
@CrossOrigin
@RestController
public class AuditSmsNotificationController {

    @Reference
    private AuditSmsNotificationService auditSmsNotificationService;

    @ApiOperation(value = "补费通知列表分页")
    @RequestMapping("/auditSmsNotification/page")
    public Result doFindAuditSmsNotificationPage(AuditSmsNotification auditSms){
        if (ObjectUtils.isNull(auditSms.getPageNum())){
            auditSms.setPageNum(1);
        }
        if (ObjectUtils.isNull(auditSms.getPageSize())){
            auditSms.setPageSize(10);
        }
        PageInfo<AuditSmsNotification> pageInfo = auditSmsNotificationService.doFindAuditSmsNotificationPage(auditSms);
        return Result.ok().data("pageInfo",pageInfo);
    }


    @ApiOperation(value = "历史补费通知列表分页")
    @RequestMapping("/auditSmsNotification/history/page")
    public Result doFindHistoryAuditSmsNotificationPage(AuditSmsNotification smsNotification){
        if (ObjectUtils.isNull(smsNotification.getPageNum())){
            smsNotification.setPageNum(1);
        }
        if (ObjectUtils.isNull(smsNotification.getPageSize())){
            smsNotification.setPageSize(10);
        }
        PageInfo<AuditSmsNotification> pageInfo = auditSmsNotificationService.doFindHistoryAuditSmsNotificationPage(smsNotification);
        return Result.ok().data("pageInfo",pageInfo);
    }
}
