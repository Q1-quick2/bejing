package com.sutong.auditDoorFrame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.auditDoorFrame.model.AuditDoorFrame;
import com.sutong.auditDoorFrame.service.AuditDoorFrameService;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.DateUtils;
import com.sutong.bjstjh.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 门架数据管理
 * @author： Mr.Kong
 * @date: 2019/12/23 11:31
 */
@CrossOrigin
@RestController
public class AuditDoorFrameController {
    private static final Logger logger = LoggerFactory.getLogger(AuditDoorFrameController.class);

    @Reference
    private AuditDoorFrameService auditDoorFrameService;

    // 绑定变量名字和属性，参数封装进类
    @InitBinder("auditDoorFrame")
    public void initBinderAuditDoorFrame(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("auditDoorFrame.");
    }


    /**
     * @description: 查询门架数据分页
     * @auther: Mr.kong
     * @date: 2019/12/23 13:54
     * @Param auditDoorFrame: 门架数据实体
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/auditDoorFrame/page")
    public Result doFindAuditDoorFramePage(@ModelAttribute("auditDoorFrame") AuditDoorFrame auditDoorFrame,
                                           @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        try {
            if (StringUtils.isNotEmpty(auditDoorFrame.getStartTimeStr()) && DateUtils.checkDateReg(auditDoorFrame.getStartTimeStr())){
                auditDoorFrame.setStartTimeDate(DateUtils.parseToDate(auditDoorFrame.getStartTimeStr(), "yyyy-MM-dd"));
            }
            if (StringUtils.isNotEmpty(auditDoorFrame.getEndTimeStr()) && DateUtils.checkDateReg(auditDoorFrame.getEndTimeStr())){
                auditDoorFrame.setEndTimeDate(DateUtils.parseToDate(auditDoorFrame.getEndTimeStr(), "yyyy-MM-dd"));
            }
            PageInfo<AuditDoorFrame> pageInfo = auditDoorFrameService.doFindAuditDoorFramePage(pageNum, pageSize, auditDoorFrame);
            return Result.ok().data("pageInfo",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("AuditDoorFrameController.doFindAuditDoorFramePage", e);
            return Result.error().message("系统异常，请稍后再试！");
        }
    }
}
