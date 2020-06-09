package com.sutong.workorder.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.workorder.model.AuditBlackListSelectDTO;
import com.sutong.workorder.model.AuditBlackListSelectQuery;
import com.sutong.workorder.model.AuditGreyListSelectDTO;
import com.sutong.workorder.model.AuditGreyListSelectQuery;
import com.sutong.workorder.service.IBlackAndGreyListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: AuditBlackAndGreyListController
 * @Description: 黑名单和灰名单相关接口
 * @author: lichengquan
 * @date: 2019年12月19日 9:46
 * @Version: 1.0
 */
@EnableSwagger2
@RestController
@CrossOrigin
@RequestMapping("/workorder")
@Api(value = "AuditBlackAndGreyListController", tags = "黑名单和灰名单相关接口")
public class AuditBlackAndGreyListController {

    @Reference
    private IBlackAndGreyListService iBlackAndGreyListService;

    /**
     * 查询黑名单
     *
     * @param auditBlackListSelectQuery
     * @return
     */
    @ApiOperation("工单管理-查询黑名单")
    @GetMapping("/doFindBlackList")
    public Result doFindBlackList(AuditBlackListSelectQuery auditBlackListSelectQuery) {
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(auditBlackListSelectQuery.getPageSize())) {
            auditBlackListSelectQuery.setPageSize(10);
        }
        if (StringUtils.isEmpty(auditBlackListSelectQuery.getPageIndex())) {
            auditBlackListSelectQuery.setPageIndex(1);
        }
        PageInfo<AuditBlackListSelectDTO> auditBlacklPage = iBlackAndGreyListService.doFindBlackList(auditBlackListSelectQuery);
        resultMap.put("pageInfo", auditBlacklPage);
        return Result.ok().data("resultData", resultMap);
    }

    /**
     * 查询灰名单
     *
     * @param auditGreyListSelectQuery
     * @return
     */
    @ApiOperation("灰名单-查询灰名单")
    @GetMapping("/doFindGreyList")
    public Result doFindGreyList(AuditGreyListSelectQuery auditGreyListSelectQuery) {
        if (StringUtils.isEmpty(auditGreyListSelectQuery.getPageIndex())) {
            auditGreyListSelectQuery.setPageIndex(1);
        }
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(auditGreyListSelectQuery.getPageSize())) {
            auditGreyListSelectQuery.setPageSize(10);
        }
        PageInfo<AuditGreyListSelectDTO> auditGreyPage = iBlackAndGreyListService.doFindGreyList(auditGreyListSelectQuery);
        resultMap.put("pageInfo", auditGreyPage);
        return Result.ok().data("resultData", resultMap);
    }


}
