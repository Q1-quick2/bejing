package com.sutong.auditBlackList.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.auditBlackList.model.AuditBlackList;
import com.sutong.auditBlackList.service.AuditBlackListService;
import com.sutong.bjstjh.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 黑名单管理
 * @author： Mr.Kong
 * @date: 2019/12/20 16:38
 */
@CrossOrigin
@RestController
public class AuditBlackListController {

    private static final Logger logger = LoggerFactory.getLogger(AuditBlackListController.class);

    @Reference
    private AuditBlackListService auditBlackListService;

    // 绑定变量名字和属性，参数封装进类
    @InitBinder("auditBlackList")
    public void initBinderAuditBlackList(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("auditBlackList.");
    }

    /**
     * @description: 查询黑名单分页
     * @auther: Mr.kong
     * @date: 2019/12/20 17:01
     * @Param auditBlackList:黑名单实体
     * @Param pageNum:当前页码
     * @Param pageSize:分页条数
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/auditBlackList/page")
    public Result doFindAuditBlackListPage(@ModelAttribute("auditBlackList")AuditBlackList auditBlackList,
                                           @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        try {
            PageInfo<AuditBlackList> pageInfo = auditBlackListService.doFindAuditBlackListPage(pageNum, pageSize, auditBlackList);
            return Result.ok().data("pageInfo",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("系统异常，查询黑名单分页失败！");
            return Result.error().message("系统异常，查询黑名单分页失败！");
        }
    }
}
