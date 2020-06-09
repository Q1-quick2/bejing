package com.sutong.auditPastOrder.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.auditPastOrder.model.AuditPastOrder;
import com.sutong.auditPastOrder.service.AuditPastOrderService;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.DateUtils;
import com.sutong.bjstjh.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 通行费差额汇总单管理
 * @author： Mr.Kong
 * @date: 2019/12/18 10:09
 */
@CrossOrigin
@RestController
public class AuditPastOrderController {

    private static final Logger logger = LoggerFactory.getLogger(AuditPastOrderController.class);

    @Reference
    private AuditPastOrderService auditPastOrderService;

    // 绑定变量名字和属性，参数封装进类
    @InitBinder("auditPastOrder")
    public void initBinderAuditPastOrder(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("auditPastOrder.");
    }

    /**
     * @description: 查询通行费差额汇总单分页
     * @auther: Mr.kong
     * @date: 2019/12/18 16:14
     * @Param auditPastOrder: 通行费差额汇总单实体
     * @Param transTimeParam: 交易时间
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/auditPastOrder/page")
    public Result doFindAuditPastOrderPage(@ModelAttribute("auditPastOrder") AuditPastOrder auditPastOrder,
                                           @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        try {
            if (StringUtils.isNotEmpty(auditPastOrder.getEndTimeStr()) && DateUtils.checkDateReg(auditPastOrder.getEndTimeStr())){
                auditPastOrder.setEndTimeDate(DateUtils.parseToDate(auditPastOrder.getEndTimeStr(), "yyyy-MM-dd"));
            }
            if (StringUtils.isNotEmpty(auditPastOrder.getStartTimeStr()) && DateUtils.checkDateReg(auditPastOrder.getStartTimeStr())){
                auditPastOrder.setStartTimeDate(DateUtils.parseToDate(auditPastOrder.getStartTimeStr(), "yyyy-MM-dd"));
            }
            PageInfo<AuditPastOrder> auditPastOrderPageInfo = auditPastOrderService.doFindAuditPastOrderPage(pageNum, pageSize, auditPastOrder);
            return Result.ok().data("auditPastOrderPageInfo",auditPastOrderPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("AuditPastOrderController.doFindAuditPastOrderPage", e);
            return Result.error().message("系统异常，请稍后再试！");
        }
    }
}
