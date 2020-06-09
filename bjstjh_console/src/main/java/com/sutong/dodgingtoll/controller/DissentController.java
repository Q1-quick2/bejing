//package com.sutong.dodgingtoll.controller;
//
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.sutong.bjstjh.result.Result;
//import com.sutong.bjstjh.util.MapUtil;
//import com.sutong.dodgingtoll.model.AuditDissentEvidence;
//import com.sutong.dodgingtoll.model.AuditDissentFlow;
//import com.sutong.dodgingtoll.service.DissentService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.util.ResourceUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * @Description:异议申请
// * @ClassName: DissentController
// * @author： lzq
// * @date: 2019/12/25 19:52
// * @Version： 1.0
// */
//@Api(tags={"异议申请"})
//@CrossOrigin
//@RestController
//@RequestMapping(value = "/dissent")
//public class DissentController {
//
//    private static Logger log = LoggerFactory.getLogger(DissentController.class);
//
//    @Reference(timeout=50000)
//    private DissentService dissentService;
//
//
//
//    @ApiOperation("保存异议申请流水")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "auditDissentFlow", value = "异议信息", required = true, dataType = "AuditDissentFlow")
//    })
//    @PostMapping(value = "/saveDissentFlow")
//    public Result saveDissentFlow(@RequestParam("file") MultipartFile file,@RequestParam Map<String,Object> map){
//
//        String url=null;
//        try {
//            String path = ResourceUtils.getURL("classpath:").getPath();
//            url=path+file.getOriginalFilename();
//            file.transferTo(new File(url));
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            Result.error().message(e.getMessage());
//        }
//
//        map.put("locationUrl",url);
//        dissentService.doInsertDissentFlow(map);
//
//
//        return Result.ok();
//
//    }
//
//
//    @ApiOperation("保存证据文件")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "file", value = "证据文件", required = true, dataType = "MultipartFile")
//    })
//    @PostMapping(value = "/uploadDissentFile")
//    public Result uploadDissentFile(@RequestParam ("file") MultipartFile file, @RequestParam Map<String,Object> map){
//
//        return Result.ok().data("url","");
//
//    }
//}
//
