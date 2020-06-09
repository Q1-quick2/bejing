package com.sutong.historyOrder.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.enumerate.DictEnum;
import com.sutong.bjstjh.exception.DataStaleException;
import com.sutong.bjstjh.exception.FilesUploadException;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.bjstjh.util.FileUtils;
import com.sutong.bjstjh.util.POIUtils;
import com.sutong.bjstjh.util.StringUtils;
import com.sutong.bjstjh.util.enumerate.FileConfigEnum;
import com.sutong.bjstjh.util.enumerate.TableColumnEnum;
import com.sutong.historyOrder.model.AuditLicensePlateDiferentEntity;
import com.sutong.historyOrder.model.AuditLicensePlateQuery;
import com.sutong.historyOrder.service.VehicleDiscrepancyService;
import com.sutong.service.IDictService;
import com.sutong.service.IExcelService;
import com.sutong.service.IFileProcessService;
import com.sutong.transfer.TransferSendSMS;
import com.sutong.workorder.model.AduitSendsmsQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@CrossOrigin
@EnableSwagger2
@RestController
@Api(value = "VehicleDiscrepancyController ", tags = "车牌不符历史工单接口")
public class VehicleDiscrepancyController {
    private static final Logger log = LoggerFactory.getLogger(VehicleDiscrepancyController.class);

    /*
     * 注入vehicleDiscrepancyService;
     * */
    @Reference
    private VehicleDiscrepancyService vehicleDiscrepancyService;
    @Reference
    private IFileProcessService fileProcessService;

    @Autowired
    private TransferSendSMS transferSendSMS;

    @Reference
    private IDictService dictService;

    @Reference
    private IExcelService excelService;

    /**
     * 根据前台条件信息查询车牌不符工单表
     *
     * @param auditLicensePlateQuery
     * @return com.sutong.bjstjh.result.Result
     */

    @ApiOperation("历史工单管理-查询车牌不符表")
    @GetMapping("/VehicleDiscrepancy")
    public Result doFindVehicleDiscrepancy(@ModelAttribute AuditLicensePlateQuery auditLicensePlateQuery) {
        Map<String, Object> resultMap = new HashMap<>();
        //如果页面不指定当前页和显示条数就指定(1,10)
        if (org.springframework.util.StringUtils.isEmpty(auditLicensePlateQuery.getPageIndex())) {
            auditLicensePlateQuery.setPageIndex(1);
        }
        if (org.springframework.util.StringUtils.isEmpty(auditLicensePlateQuery.getPageSize())) {
            auditLicensePlateQuery.setPageSize(10);
        }
        PageInfo<AuditLicensePlateDiferentEntity> FindVehicle = vehicleDiscrepancyService.doFindVehicle(auditLicensePlateQuery);


        resultMap.put("pageInfo", FindVehicle);

        return Result.ok().data("resultMap", resultMap);
    }

    /**
     * 创建车牌不符表和上传证据
     *
     * @param multipartFile
     * @param auditLicensePlateDiferentEntity
     * @return
     */
    @ApiOperation("历史工单管理-创建车牌不符工单表")
    @PostMapping("/doInsertVehicle")
    public Result doInsertVehicleDiscrepancy(@RequestParam(value = "file", required = false) MultipartFile[] multipartFile,
                                             @ModelAttribute AuditLicensePlateDiferentEntity auditLicensePlateDiferentEntity) {
        String tableID = StringUtils.generateUUID();
        auditLicensePlateDiferentEntity.setSerialId(tableID);
        if (multipartFile != null) {
            for (MultipartFile file : multipartFile) {
                try {
                    //调用pwz上传方法,换表名和表字段
                    Map<String, String> upload2 = FileUtils.upload2(file, FileConfigEnum.AUDIT_LICENSE_PLATE_DIFERENT,

                            TableColumnEnum.PROOF, tableID);
                    //将返回的信息插入到数据库...
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
        //将主业务表信息插入到数据库 ...
        vehicleDiscrepancyService.doInsertVehicleDiscrepancy(auditLicensePlateDiferentEntity);
        //返回结果
        return Result.ok();
    }

    /**
     * @description: 查询车牌不符工单详情
     * @return: com.sutong.bjstjh.result.Result
     **/
    @ApiOperation("查询车牌不符详情表")
    @GetMapping("/doFindVehicleResultInfo")
    public Result doFindVehicleResultInfo(@RequestParam("serialId") String serialId) {
        Map<String, Object> resultMap = new HashMap<>();
        //查询车牌不符详情表
        AuditLicensePlateDiferentEntity auditLicensePlateDiferentEntity =
                vehicleDiscrepancyService.doFindVehicleResultInfo(serialId);
        //查询车牌不符工单证据url

        List<String> filepath = fileProcessService.queryFilepath(FileConfigEnum.AUDIT_LICENSE_PLATE_DIFERENT, serialId);

        resultMap.put("filepath", filepath);
        resultMap.put("auditLicensePlateDiferentEntity", auditLicensePlateDiferentEntity);
        return Result.ok().data("resultMap", resultMap);
    }

    /**
     * 车牌不符变更状态
     *
     * @param serialId
     * @param status
     * @return
     */
    @ApiOperation("车牌不符变更状态")
    @PostMapping("/doupdate/{serialId}/{status}")
    public Result doupdate(@PathVariable("serialId") String serialId, @PathVariable("status") Integer status) {

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("serialId", serialId);
        hashMap.put("status", status);

        Integer result = vehicleDiscrepancyService.doUpdate(hashMap);

        if (result > 0) {
            return Result.ok();
        } else {

            return Result.error();
        }
    }

    /**
     * 手动发短信和定时发短信
     *
     * @param aduitSendsmsQueries
     * @return
     */
    @ApiOperation("车牌不符手动发送短信")
    @PostMapping("/sendSms")
    public Result sendSms(@RequestBody List<AduitSendsmsQuery> aduitSendsmsQueries) {
        if (CollectionUtils.isEmpty(aduitSendsmsQueries)) {
            return Result.error().message("通知短信电话不能为空");
        }
        String stringMessage = "车牌不符通知短信";
        List<String> successList = new ArrayList<>();
        for (AduitSendsmsQuery aduitSendsmsQuery : aduitSendsmsQueries) {
//            Map<String, Object> resultMap = transferSendSMS.sendSMS(aduitSendsmsQuery.getContactTelePhoneNo(), message);
            Map resultMap = new HashMap<String, Object>();
            resultMap.put("success", true);
            if (!resultMap.containsValue(true)) {
                return Result.error().message("发送短信失败");
            } else {
                successList.add(aduitSendsmsQuery.getSerialId());
            }
        }
        Integer success = vehicleDiscrepancyService.doUpdateVehicle(successList);
        if (success == successList.size()) {
            return Result.ok();
        } else {
            return Result.error().message("更新状态失败");
        }

    }

    /**
     * 导入车牌不符工单表
     *
     * @param excelFile
     * @return
     */
    @ApiOperation("车牌不符工单管理-导入车牌不符工单表")
    @PostMapping("/doInsertVehicleTable")
    public Result doInsertVehicleTable(@RequestParam("excelFile") MultipartFile excelFile) {
    	try {
    		List<Object> mapByVeh = POIUtils.caseExcelMapByVeh(excelFile);
    		Map<String,String> titleMap = (Map<String, String>) mapByVeh.get(0);
    		Map<String, List<Object>> excelMap = (Map<String, List<Object>>) mapByVeh.get(1);
    		//根据交易时间和IC卡号确定数据的唯一性，用于判断数据是否重复导入
    		List<Object> transTimeList = excelMap.get("交易时间");
    		List<Object> icCardList = excelMap.get("IC卡号");
    		if(transTimeList==null || icCardList==null)
    			throw new DataStaleException(ResultCode.ERROR, "Excel文件中未检测到交易时间或IC卡号");
    		List<AuditLicensePlateDiferentEntity> etcAndTransTime =
    				vehicleDiscrepancyService.doFindTableByETCAndTransactiontime(transTimeList, icCardList);
    		//如果不为空，说明数据存在
    		if(etcAndTransTime != null && etcAndTransTime.size() != 0) {
    			List<AuditLicensePlateDiferentEntity> collect =
    					etcAndTransTime.stream().distinct().collect(Collectors.toList());
    			if(collect != null) {
    				List<String> resultMsg = new ArrayList<String>();
    				for (AuditLicensePlateDiferentEntity entity : collect) {
    					resultMsg.add("IC卡号："+entity.getEtcNumber()+"，交易时间："+entity.getTransactionTime());
    				}
    				throw new DataStaleException(ResultCode.DATA_ERROR,
    						"共有"+collect.size()+"项数据重复导入，分别是："+resultMsg.toString()+";");
    			}
    		}
    		List<AuditLicensePlateDiferentEntity> setFileds =
    				excelService.setFileds(new AuditLicensePlateDiferentEntity(), excelMap, IExcelService.Relaxed);
    		Integer doFindDictNumber =
    				dictService.doFindDictNumber(DictEnum.EVENT_TYPECODE.toString(), titleMap.get("事件类别").trim());
    		if(doFindDictNumber == null)
    			throw new DataStaleException(ResultCode.DATA_ERROR, "未匹配到正确的字典项");
    		for (AuditLicensePlateDiferentEntity entity : setFileds) {
    			entity.setVerificationParty(titleMap.get("来文单位"));
    			entity.setCreateTime(titleMap.get("事件发生时间"));
    			entity.setChargePerson(titleMap.get("经办人"));
    			entity.setChargePersonPhone(titleMap.get("联系电话"));
    			entity.setDetermine(doFindDictNumber);//字典
    			entity.setEventPass(titleMap.get("事件经过"));
    		}
    		vehicleDiscrepancyService.doInsertVehicleTable(setFileds);
    		return Result.ok();
    	} catch (DataStaleException e) {
    		e.printStackTrace();
    		return Result.error(ResultCode.ERROR, e.getMsg());
    	} catch (Exception e) {
    		e.printStackTrace();
    		return Result.error(ResultCode.ERROR, "文件解析失败,请检查格式或内容");
    	}
    }

}



