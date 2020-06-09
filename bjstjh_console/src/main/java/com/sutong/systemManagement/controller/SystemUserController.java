package com.sutong.systemManagement.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.StringUtils;
import com.sutong.systemManagement.model.entity.AuditSystemRoleManage;
import com.sutong.systemManagement.model.entity.AuditSystemUserManage;
import com.sutong.systemManagement.service.SystemRoleService;
import com.sutong.systemManagement.service.SystemUserService;
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
@Api(value = "SystemUserController ", tags = "系统管理_用户管理")
public class SystemUserController {


    private static final Logger log = LoggerFactory.getLogger(SystemUserController.class);

    @Reference
    private SystemUserService systemUserService;


    @Reference
    private SystemRoleService systemRoleService;

    /**
     * 查询用户管理
     * @param systemUserManage
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("系统管理-查询用户管理")
    @GetMapping("/doFindUserManage")
    public Result doFindUserManage(@ModelAttribute("systemUserManage") AuditSystemUserManage systemUserManage,
                                   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PageInfo<AuditSystemUserManage> pageInfo = systemUserService.doFindUserManage(systemUserManage, pageNum, pageSize);

        return Result.ok().data("pageInfo", pageInfo);
    }

    /**
     * 查询用户对应的全部角色
     * @param systemRoleManage
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("系统管理-查询用户对应的全部角色")
    @GetMapping("/doFindUserRoleManage")
    public Result doFindUserRoleManage(@ModelAttribute("systemRoleManage") AuditSystemRoleManage systemRoleManage,
                                       @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        PageInfo<AuditSystemRoleManage> pageInfo = systemRoleService.doFindRoleManage(systemRoleManage, pageNum, pageSize);

        return Result.ok().data("pageInfo", pageInfo);
    }

    /**
     * 创建用户管理
     * @param systemUserManage
     * @param roleIds
     * @return
     */
    @ApiOperation("系统管理-创建用户管理")
    @PostMapping("/doInsertUserManage")
    public Result doInsertUserManage(@ModelAttribute("systemUserManage") AuditSystemUserManage systemUserManage, String[] roleIds) {

        String tableID = StringUtils.generateUUID();
        systemUserManage.setUserId(tableID);
        try {
            systemUserService.doInsertUserManage(systemUserManage, roleIds);

            //返回结果
            return Result.ok();
        } catch (Exception e) {
            return Result.error();
        }
    }

    /**
     * 修改用户管理
     * @param systemUserManage
     * @param roleIds
     * @return
     */
    @ApiOperation("系统管理-修改用户管理")
    @PostMapping("/doUpdateUserManage")
    public Result doUpdateUserId(@ModelAttribute("systemUserManage") AuditSystemUserManage systemUserManage, String[] roleIds) {
        try {
            systemUserService.doUpdateUserId(systemUserManage, roleIds);
            //返回结果
            return Result.ok();
        } catch (Exception e) {
            return Result.error();
        }
    }

    /**
     * 删除用户管理
     * @param userId
     * @return
     */
    @ApiOperation("系统管理-删除用户管理")
    @GetMapping("/doDeleteUserManage")
    public Result doDeleteUserId(@RequestParam("userId") String userId) {
        try {
            systemUserService.doDeleteUserId(userId);
            //返回结果
            return Result.ok();
        } catch (Exception e) {
            return Result.error();
        }
    }

}
