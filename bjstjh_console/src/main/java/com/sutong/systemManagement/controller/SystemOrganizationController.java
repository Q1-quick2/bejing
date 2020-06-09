package com.sutong.systemManagement.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.StringUtils;
import com.sutong.systemManagement.model.entity.AuditSystemOrganizationManage;
import com.sutong.systemManagement.service.SystemOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author Administrator
 */
@CrossOrigin
@EnableSwagger2
@RestController
@Api(value = "SystemManagementController ", tags = "系统管理_机构管理")
public class SystemOrganizationController {

    private static final Logger log = LoggerFactory.getLogger(SystemOrganizationController.class);


    @Reference
    private SystemOrganizationService systemOrganizationService;

    /**
     * 根据条件查询系统管理
     *
     * @param systemOrganizationManage
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("系统管理-查询机构管理")
    @GetMapping("/doFindOrganizationManage")
    public Result doFindOrganizationManage(@ModelAttribute("systemOrganizationManage") AuditSystemOrganizationManage systemOrganizationManage,
                                           @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PageInfo<AuditSystemOrganizationManage> pageInfo = systemOrganizationService.doFindOrganizationManage(systemOrganizationManage, pageNum, pageSize);

        return Result.ok().data("pageInfo", pageInfo);
    }

    /**
     * 创建机构管理
     *
     * @param systemOrganizationManage
     * @return
     */
    @ApiOperation("系统管理-创建机构管理")
    @PostMapping("/doInsertOrganization")
    public Result doInsertOrganizationManage(@ModelAttribute("systemOrganizationManage") AuditSystemOrganizationManage systemOrganizationManage) {

        String tableID = StringUtils.generateUUID();
        systemOrganizationManage.setOrganizationId(tableID);

        systemOrganizationService.doInsertOrganizationManage(systemOrganizationManage);
        //返回结果
        return Result.ok();
    }

    /**
     * 查询机构管理详情
     *
     * @param organizationId
     * @return
     */
    @ApiOperation("系统管理-查询机构管理详情")
    @GetMapping("/doFindOrganizationInfo")
    public Result doFindOrganizationId(@RequestParam("organizationId") String organizationId) {

        AuditSystemOrganizationManage SystemOrganizationManage = systemOrganizationService.doFindOrganizationId(organizationId);
        return Result.ok().data("resultMap", SystemOrganizationManage);
    }

    /**
     * 修改机构管理
     *
     * @param systemOrganizationManage
     * @return
     */
    @ApiOperation("系统管理-修改机构管理")
    @PostMapping("/doUpdateOrganizationInfo")
    public Result doUpdateOrganizationId(@ModelAttribute("systemOrganizationManage") AuditSystemOrganizationManage systemOrganizationManage) {

        systemOrganizationService.doUpdateOrganizationId(systemOrganizationManage);
        return Result.ok();
    }

    /**
     * 系统管理-删除机构管理
     *
     * @param organizationId
     * @return
     */
    @ApiOperation("系统管理-删除机构管理")
    @GetMapping("/doDeleteOrganizationInfo")
    public Result doDeleteOrganizationId(@RequestParam("organizationId") String organizationId) {

        systemOrganizationService.doDeleteOrganizationId(organizationId);
        return Result.ok();
    }


}
