package com.sutong.auditManualMinistryWorkOrder.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.auditCheckResults.service.AuditCheckResultsService;
import com.sutong.auditIssueVeh.service.AuditIssueVehService;
import com.sutong.auditManualMinistryWorkOrder.model.AuditManualMinistryWorkOrderModel;
import com.sutong.auditManualMinistryWorkOrder.service.AuditManualMinistryWorkOrderService;
import com.sutong.auditRealVehInfo.service.AuditRealVehInfoService;
import com.sutong.bjstjh.entity.AuditCheckResults;
import com.sutong.bjstjh.entity.AuditIssueVeh;
import com.sutong.bjstjh.entity.AuditManualMinistryWorkOrder;
import com.sutong.bjstjh.entity.AuditRealVehInfo;
import com.sutong.bjstjh.exception.BaseException;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.bjstjh.util.FileUtils;
import com.sutong.bjstjh.util.enumerate.FileConfigEnum;
import com.sutong.bjstjh.util.enumerate.TableColumnEnum;
import com.sutong.service.IFileProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin
@EnableSwagger2
@RestController
@RequestMapping("/AuditManualMinistryWorkOrderController")
@Api(value = "部级工单操作接口",tags = "部级工单操作接口")
public class AuditManualMinistryWorkOrderController {

	private static final Logger log = LoggerFactory.getLogger(AuditManualMinistryWorkOrderController.class);

    @Reference
    private AuditManualMinistryWorkOrderService auditManualMinistryWorkOrderService;
    @Reference
    private AuditIssueVehService auditIssueVehService;
    @Reference
    private AuditCheckResultsService auditCheckResultsService;
    @Reference
    private AuditRealVehInfoService auditRealVehInfoService;
    @Reference
    private IFileProcessService fileProcessService;

//    部级工单保存稽核之后的结果
    @ApiOperation("保存")
    @PostMapping("/doInsertAuditManualMinistryWorkOrder")
    public Result doInsertAuditManualMinistryWorkOrder(@ModelAttribute AuditManualMinistryWorkOrder auditManualMinistryWorkOrder){
        Integer integer=auditManualMinistryWorkOrderService.doInsertAuditManualMinistryWorkOrder(auditManualMinistryWorkOrder);
        if(integer>0){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
//    部级工单列表条件和分页查询
    @ApiOperation("工单列表条件查询")
    @GetMapping("/doFindAuditManualMinistryWorkOrder")
    public Result doFindAuditManualMinistryWorkOrder(@ModelAttribute AuditManualMinistryWorkOrderModel auditManualMinistryWorkOrderModel){
		Map<String, Object> resultMap = new HashMap<>();
		if (StringUtils.isEmpty(auditManualMinistryWorkOrderModel.getPageIndex())) {
			auditManualMinistryWorkOrderModel.setPageIndex(1);
		}
		if (StringUtils.isEmpty(auditManualMinistryWorkOrderModel.getPageSize())) {
			auditManualMinistryWorkOrderModel.setPageSize(10);
		}
		PageInfo<AuditManualMinistryWorkOrder> pageInfo = auditManualMinistryWorkOrderService.doFindAuditManualMinistryWorkOrder(auditManualMinistryWorkOrderModel);

		resultMap.put("pageInfo", pageInfo);

		return Result.ok().data("resultMap", resultMap);
    }
//    部级工单详情查询根据主键
    @ApiOperation("部级工单详情查询")
    @GetMapping("/doFindAuditManualMinistryWorkOrder/{ministryWorkOrderId}")
    public AuditManualMinistryWorkOrder doFindAuditManualMinistryWorkOrder(@PathVariable(value = "ministryWorkOrderId") String ministryWorkOrderId){
        return auditManualMinistryWorkOrderService.doFindAuditManualMinistryWorkOrder(ministryWorkOrderId);
    }

    @ApiOperation("部级工单打包下载")
    @GetMapping("/doDownloadAudit")
    public Result doDownloadAudit(HttpServletResponse response,String id) {
    	if(id == null || id.isEmpty()){
			return Result.error();
		}
    	List<Object> objList = new ArrayList<Object>();
    	List<String> pathList = new ArrayList<String>();
    	//1 去数据库查询四个类
    	AuditManualMinistryWorkOrder ministryWorkOrder = auditManualMinistryWorkOrderService.doFindAuditManualMinistryWorkOrder(id);
    	if(ministryWorkOrder == null){
			return Result.error(ResultCode.DATA_ERROR,"没有查到符合["+id+"]的数据");
		}
    	AuditIssueVeh issueVeh = auditIssueVehService.doFindAuditIssueVeh(id);
    	if (issueVeh != null) {
    		objList.add(issueVeh);
    		List<String> queryFilepath = fileProcessService.queryFilepath(FileConfigEnum.AUDIT_ISSUE_VEH_TABLE,
    				issueVeh.getIssueVehId());
    		if(queryFilepath != null) {
    			for (String string : queryFilepath) {
        			pathList.add(string);
        		}
    		}
    	}
    	AuditCheckResults checkResults = auditCheckResultsService.doFindAuditCheckResults(id);
    	if (checkResults != null)	{
    		objList.add(checkResults);
    		List<String> checkResultsPath = fileProcessService.queryFilepathByColumn(FileConfigEnum.AUDIT_CHECK_RESULTS,
    				checkResults.getCheckResultsId() , TableColumnEnum.IMG_ADDRESS);
    		if(checkResultsPath != null) {
    			for (String string : checkResultsPath) {
    				pathList.add(string);
    			}
    		}
    	}
    	AuditRealVehInfo realVehInfo = auditRealVehInfoService.doFindAuditRealVehInfo(id);
    	if (realVehInfo != null) {
    		objList.add(realVehInfo);
    	}
    	//最后一个放进去的实体类,zip命名开头就是类名
    	if (ministryWorkOrder != null) {
			objList.add(ministryWorkOrder);
		}
    	String zip = "";
		try {
			zip = FileUtils.zipOne(objList, null, pathList);
			FileUtils.download(response, zip,true);
		} catch (BaseException e) {
			log.error("数据不存在,无法下载");
			e.printStackTrace();
			return Result.error(ResultCode.DATA_ERROR, "数据不存在,无法下载");
		}
    	return Result.ok();
    }

}
