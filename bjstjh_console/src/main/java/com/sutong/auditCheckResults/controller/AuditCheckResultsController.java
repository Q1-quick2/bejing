package com.sutong.auditCheckResults.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.auditCheckResults.service.AuditCheckResultsService;
import com.sutong.bjstjh.entity.AuditCheckResults;
import com.sutong.bjstjh.exception.FilesUploadException;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.FileUtils;
import com.sutong.bjstjh.util.StringUtils;
import com.sutong.bjstjh.util.enumerate.FileConfigEnum;
import com.sutong.bjstjh.util.enumerate.TableColumnEnum;
import com.sutong.service.IFileProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;

@CrossOrigin
@EnableSwagger2
@RestController
@RequestMapping("/AuditCheckResultsController")
@Api(value = "部级稽核结果操作接口",tags = "部级稽核结果操作接口")
public class AuditCheckResultsController {
    @Reference
    private AuditCheckResultsService auditCheckResultsService;
    @Reference
    private IFileProcessService fileProcessService;

    private static final Logger log = LoggerFactory.getLogger(AuditCheckResultsController.class);

//    部级工单保存稽核之后的结果
    @ApiOperation("保存")
    @PostMapping("/doInsertAuditCheckResults")
    public Result doInsertAuditCheckResults(@RequestParam(value = "file",required = false) MultipartFile[] multipartFile, @ModelAttribute AuditCheckResults auditCheckResults) {
//        生成稽核结论对比结果的表的主键，同时作为本条数据图片表的TABLEID
        String tableId = StringUtils.generateUUID();
        auditCheckResults.setCheckResultsId(tableId);
//       判断文件是否为空，不为空就进入上传方法，上传出错就抛出异常，否则继续执行后续代码
        if (multipartFile != null) {
            for (MultipartFile file : multipartFile) {
                try {
                    Map<String, String> upload2 = FileUtils.upload2(file, FileConfigEnum.AUDIT_CHECK_RESULTS, TableColumnEnum.IMG_ADDRESS, tableId);
//                    将返回的信息插入数据库
                    Integer saveFilepath = fileProcessService.saveFilepath(upload2);
                    if (saveFilepath < 1) {
                        log.error("数据插入到数据库失败:" + upload2);
                        return Result.error();
                    }
                } catch (FilesUploadException e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                    return Result.error();
                }
            }
        }
        //        先删除再插入等于更新，用同一个service接口里的同一个方法
        Integer integer = auditCheckResultsService.doInsertAuditCheckResults(auditCheckResults);
        if (integer > 0) {
            return Result.ok();

        } else {
            return Result.error();
        }
    }
//    部级稽核结论对比结果根据外键查询
    @ApiOperation("查询")
    @GetMapping("/doFindAuditCheckResults/{ministryWorkOrderForeignId}")
    public AuditCheckResults doFindAuditCheckResults(@PathVariable(value = "ministryWorkOrderForeignId") String ministryWorkOrderForeignId){
        return auditCheckResultsService.doFindAuditCheckResults(ministryWorkOrderForeignId);
    }
}
