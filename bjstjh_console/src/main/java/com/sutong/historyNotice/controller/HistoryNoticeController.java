package com.sutong.historyNotice.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.dodgingtoll.model.AuditPastOrder;
import com.sutong.historyNotice.service.HistoryNoticeService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: HistoryNoticeController
 * @Description
 * @Author: Mr.Su
 * @Date: 2019/12/18 14:34
 * @Version V1.0
 **/
@RestController
@CrossOrigin
public class HistoryNoticeController {

   @Reference
    private HistoryNoticeService historyNoticeService;

    //绑定变量名字和属性，参数封装进类
    @InitBinder("auditPastOrder")
    public void initBinderRunFee(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("auditPastOrder.");
    }

    @RequestMapping("/historynotice/insert")
    public Result doInsertHistoryNotice(@ModelAttribute("auditPastOrder")AuditPastOrder auditPastOrder){
        historyNoticeService.doInsertHistoryNoticeSelective(auditPastOrder);
        return Result.ok();
    }

    /*
     * @description:查询客户历史补费通知列表分页
     * @auther: Mr.Su
     * @date: 2019/12/19 15:14
     * @Param auditPastOrder:
     * @Param pageNum:
     * @Param pageSize:
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/historynotice/page")
    public Result doFindAuditPastOrderPage(@ModelAttribute("auditPastOrder")AuditPastOrder auditPastOrder,
                                   @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                   @RequestParam(value = "pageSize",defaultValue = "10")int pageSize ){

        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageInfo<AuditPastOrder> pageInfo = historyNoticeService.doFindAuditPastOrderPage(pageNum, pageSize, auditPastOrder);
        return Result.ok().data("pageInfo",pageInfo);
    }

    /*
     * @description: 查询客户历史补费通知列表（手动通知）
     * @auther: Mr.Su
     * @date: 2019/12/19 10:47
     * @Param auditPastOrder: 通行费差额汇总单
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/historynotice/list")
    public Result doFindAuditPastOrderList(@ModelAttribute("auditPastOrder")AuditPastOrder auditPastOrder){
        List<AuditPastOrder> auditPastOrderList = historyNoticeService.doFindAuditPastOrderList(auditPastOrder);
        return Result.ok().data("auditPastOrderList",auditPastOrderList);
    }

    /*
     * @description: 查询客户历史补费通知列表（自动通知）
     * @auther: Mr.Su
     * @date: 2019/12/19 10:48
     * @Param auditPastOrder: 通行费差额汇总单
     * @return: com.sutong.bjstjh.result.Result
     **/
    /*@RequestMapping("/historyNoticeAuto/list")
    public Result doFindAuditPastOrderAutoList(@ModelAttribute("auditPastOrder")AuditPastOrder auditPastOrder){
        List<AuditPastOrder> auditPastOrderAutoList = historyNoticeService.doFindAuditPastOrderAutoList(auditPastOrder);
        return Result.ok().data("auditPastOrderAutoList",auditPastOrderAutoList);
    }*/
}
