package com.sutong.auditRealVehInfo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.auditRealVehInfo.service.AuditRealVehInfoService;
import com.sutong.bjstjh.entity.AuditRealVehInfo;
import com.sutong.bjstjh.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@CrossOrigin
@EnableSwagger2
@RestController
@RequestMapping("/AuditRealVehInfoController")
@Api(value = "部级实际车辆信息操作接口",tags = "部级实际车辆信息操作接口")
public class AuditRealVehInfoController {
    @Reference
    private AuditRealVehInfoService auditRealVehInfoService;
    //    部级工单保存稽核之后的结果
    @ApiOperation("保存")
    @PostMapping("/doInsertAuditRealVehInfo")
    public Result doInsertAuditRealVehInfo(@ModelAttribute AuditRealVehInfo auditRealVehInfo){

        //        先删除再插入等于更新，用同一个service接口里的同一个方法

        Integer integer = auditRealVehInfoService.doInsertAuditRealVehInfo(auditRealVehInfo);
        if(integer>0){
            return Result.ok();

        }else {
            return Result.error();
        }
    }
//    实际车辆信息根据外键查询
    @ApiOperation("查询")
    @GetMapping("/doFindAuditRealVehInfo/{ministryWorkOrderForeignId}")
    public AuditRealVehInfo doFindAuditRealVehInfo(@PathVariable(value = "ministryWorkOrderForeignId") String ministryWorkOrderForeignId){
        return auditRealVehInfoService.doFindAuditRealVehInfo(ministryWorkOrderForeignId);
    }
}
