package com.sutong.workorder.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.workorder.entity.AuditLogManagementEntity;
import com.sutong.workorder.model.LogManagementQuery;
import com.sutong.workorder.service.IWorkOrderService;
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
 * @ClassName: WorkOrderController
 * @Description: 工单相关controller
 * @author: lichengquan
 * @date: 2019年12月17日 16:25
 * @Version: 1.0
 */
@EnableSwagger2
@RestController
@CrossOrigin
@RequestMapping("/workorder")
@Api(value = "WorkOrderController", tags = "工单相关接口")
public class WorkOrderController {
    @Reference
    private IWorkOrderService iWorkOrderService;

    /**
     * 保存日志
     *
     * @param auditLogManagement
     * @return
     */
    @ApiOperation("日志管理-保存日志")
    @GetMapping("/saveLogManagement")
    public Result doInsertLogManagement(AuditLogManagementEntity auditLogManagement) {
        if (auditLogManagement == null) {
            return Result.error().message("日志不能为空");
        }
        if (StringUtils.isEmpty(auditLogManagement.getSystemName())) {
            return Result.error().message("系统方名称不能为空");
        }
        if (StringUtils.isEmpty(auditLogManagement.getSystemCode())) {
            return Result.error().message("系统方编码不能为空");
        }
        if (StringUtils.isEmpty(auditLogManagement.getInterfaceName())) {
            return Result.error().message("接口名称不能为空");
        }
        if (StringUtils.isEmpty(auditLogManagement.getRequestParameterInfo())) {
            return Result.error().message("请求参数信息不能为空");
        }
        if (StringUtils.isEmpty(auditLogManagement.getReturnParameterInfo())) {
            return Result.error().message("返回参数信息不能为空");
        }
        Boolean insertFlag = iWorkOrderService.doInsertLogManagement(auditLogManagement);
        if (!insertFlag) {
            return Result.error().message("插入表失败");
        }
        return Result.ok();
    }

    /**
     * 查询日志
     *
     * @param logManagementQuery
     * @return
     */
    @ApiOperation("日志管理-查询日志")
    @GetMapping("/getLogManagement")
    public Result doFindLogManagement(LogManagementQuery logManagementQuery) {
        Map<Object, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(logManagementQuery.getPageIndex())) {
            logManagementQuery.setPageIndex(1);
        }
        if (StringUtils.isEmpty(logManagementQuery.getPageSize())) {
            logManagementQuery.setPageSize(10);
        }
        PageInfo<AuditLogManagementEntity> logManagementPage = iWorkOrderService.doFindLogManagement(logManagementQuery);
        resultMap.put("pageInfo", logManagementPage);
        return Result.ok().data("resultData", resultMap);
    }
}
