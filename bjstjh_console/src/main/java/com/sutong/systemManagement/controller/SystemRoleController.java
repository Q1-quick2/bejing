package com.sutong.systemManagement.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.StringUtils;
import com.sutong.systemManagement.model.entity.AuditSystemRoleManage;
import com.sutong.systemManagement.service.SystemRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin
@EnableSwagger2
@RestController
@Api(value = "SystemRoleController ", tags = "系统管理_角色管理")
public class SystemRoleController {

    private static final Logger log = LoggerFactory.getLogger(SystemRoleController.class);

    @Reference
    private SystemRoleService systemRoleService;

    /**
     * 查询角色管理
     *
     * @param systemRoleManage
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("系统管理-查询角色管理")
    @GetMapping("/doFindRoleManage")
    public Result doFindRoleManage(@ModelAttribute("systemRoleManage") AuditSystemRoleManage systemRoleManage,
                                   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PageInfo<AuditSystemRoleManage> pageInfo = systemRoleService.doFindRoleManage(systemRoleManage, pageNum, pageSize);

        return Result.ok().data("pageInfo", pageInfo);
    }

    /**
     * 创建角色管理
     *
     * @param systemRoleManage
     * @return
     */
    @ApiOperation("系统管理-创建角色管理")
    @PostMapping("/doInsertRoleManage")
    public Result doInsertRoleManage(@ModelAttribute("systemRoleManage") AuditSystemRoleManage systemRoleManage) {

        String tableID = StringUtils.generateUUID();
        systemRoleManage.setRoleId(tableID);

        systemRoleService.doInsertRoleManage(systemRoleManage);
        //返回结果
        return Result.ok();
    }

    /**
     * 查询角色管理详情
     *
     * @param roleId
     * @return
     */
    @ApiOperation("系统管理-查询角色管理详情")
    @GetMapping("/doFindRoleManageInfo")
    public Result doFindRoleId(@RequestParam("roleId") String roleId) {

        AuditSystemRoleManage systemRoleManage = systemRoleService.doFindRoleId(roleId);
        return Result.ok().data("resultMap", systemRoleManage);
    }

    /**
     * 修改角色管理
     *
     * @param systemRoleManage
     * @return
     */
    @ApiOperation("系统管理-修改角色管理")
    @PostMapping("/doUpdateRoleManage")
    public Result doUpdateRoleId(@ModelAttribute("systemRoleManage") AuditSystemRoleManage systemRoleManage) {

        systemRoleService.doUpdateRoleId(systemRoleManage);
        return Result.ok();
    }

    /**
     * 删除角色管理
     *
     * @param roleId
     * @return
     */
    @ApiOperation("系统管理-删除角色管理")
    @GetMapping("/doDeleteRoleManage")
    public Result doDeleteRoleId(@RequestParam("roleId") String roleId) {

        systemRoleService.doDeleteRoleId(roleId);
        return Result.ok();
    }


}
