package com.sutong.workorder.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.exception.BreakRulesException;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.workorder.entity.AuditWorkOrderTableEntity;
import com.sutong.workorder.model.*;
import com.sutong.workorder.service.IAuditConclusionService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @ClassName: AuditConclusionController
 * @Description: 稽核结论相关接口
 * @author: lichengquan
 * @date: 2020年01月08日 14:46
 * @Version: 1.0
 */
@EnableSwagger2
@RestController
@CrossOrigin
@RequestMapping("/workorder")
@Api(value = "AuditConclusionController", tags = "稽核结论相关接口")
public class AuditConclusionController {

	private static final Logger log = LoggerFactory.getLogger(AuditConclusionController.class);

    @Reference
    private IAuditConclusionService iAuditConclusionService;

    @ApiOperation("稽核结论-查询稽核结论")
    @GetMapping("/doFindConclusionList")
    public Result doFindConclusionList(AuditCheckCommentsQuery auditCheckCommentsQuery) {
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(auditCheckCommentsQuery.getPageIndex())) {
            auditCheckCommentsQuery.setPageIndex(1);
        }
        if (StringUtils.isEmpty(auditCheckCommentsQuery.getPageSize())) {
            auditCheckCommentsQuery.setPageSize(10);
        }
        PageInfo<AuditCheckCommentsDTO> auditCheckCommentsPage = iAuditConclusionService.doFindConclusionList(auditCheckCommentsQuery);
        resultMap.put("pageInfo", auditCheckCommentsPage);
        return Result.ok().data("resultData", resultMap);
    }

    @ApiOperation("稽核结论-稽核结论详情查询")
    @GetMapping("/doFindOneClnclusion")
    public Result doFindOneClnclusion(@RequestParam("workOrderId") String workOrderId) {
        Result result = Result.error();
        if (StringUtils.isEmpty(workOrderId)) {
            return result.message("稽核数据编号不能为空");
        } else {
            Map<String, Object> resultMap = new HashMap<>();
            AuditConclusionDTO auditConclusion = iAuditConclusionService.getAuditConclusion(workOrderId);
            resultMap.put("result", auditConclusion);
            result = Result.ok().data("resultData", resultMap);
        }
        return result;
    }

    @ApiOperation("外部稽核-稽核数据清单查询")
    @GetMapping("/doFindAuditDataList")
    public Result doFindAuditDataList(AuditDataListQuery auditDataListQuery) {
        Map<String, Object> dataMap = new HashMap<>();
        if (StringUtils.isEmpty(auditDataListQuery.getPageIndex())) {
            auditDataListQuery.setPageIndex(1);
        }
        if (StringUtils.isEmpty(auditDataListQuery.getPageSize())) {
            auditDataListQuery.setPageSize(10);
        }
        PageInfo<AuditDataListDTO> auditDataListDtoPageInfo = iAuditConclusionService.doFindAuditDataList(auditDataListQuery);
        dataMap.put("pageInfo", auditDataListDtoPageInfo);
        return Result.ok().data("result", dataMap);
    }

    @ApiOperation("外部稽核-创建稽核工单查询")
    @GetMapping("/doFindWorkOrderQuery")
    public Result doFindWorkOrderQuery(@RequestParam("workOrderId") String workOrderId) {
        Result result = Result.error();
        if (StringUtils.isEmpty(workOrderId)) {
            return result.message("稽核数据主键不能为空");
        } else {
            Map<String, Object> resultMap = new HashMap<>();
            AuditAuditorListDTO auditorListDto = iAuditConclusionService.createWorkOrderQuery(workOrderId);
            resultMap.put("result", auditorListDto);
            result = Result.ok().data("resultData", resultMap);
        }
        return result;
    }

    @ApiOperation("外部稽核-待处理查询")
    @GetMapping("/doPendingDisposalList")
    public Result doPendingDisposalList(AuditPendDisposalQuery auditPendDisposalQuery) {
        Map<String, Object> dataMap = new HashMap<>();
        if (StringUtils.isEmpty(auditPendDisposalQuery.getPageIndex())) {
            auditPendDisposalQuery.setPageIndex(1);
        }
        if (StringUtils.isEmpty(auditPendDisposalQuery.getPageSize())) {
            auditPendDisposalQuery.setPageSize(10);
        }
        PageInfo<AuditPendDisposalDTO> auditPendDisposalPageInfo = iAuditConclusionService.doPendingDisposalList(auditPendDisposalQuery);
        dataMap.put("pageInfo", auditPendDisposalPageInfo);
        return Result.ok().data("resultData", dataMap);
    }

    @ApiOperation("外部稽核-详情查看-数据概况")
    @GetMapping("/doFindDataSurvey")
    public Result doFindDataSurvey(@RequestParam("workOrderId") String workOrderId) {
        Result result = Result.error();
        if (StringUtils.isEmpty(workOrderId)) {
            result = Result.error().message("稽核数据主键不能为空");
        } else {
            Map<String, Object> resultMap = new HashMap<>();
            AuditDataSurveyDTO auditDataSurveyDto = iAuditConclusionService.doFindDataSurvey(workOrderId);
            resultMap.put("result", auditDataSurveyDto);
            result = Result.ok().data("resultData", resultMap);
        }
        return result;
    }

    @ApiOperation("外部稽核-稽核数据清单-出入口数据")
    @GetMapping("/doFindEntryAndExit")
    public Result doFindEntryAndExitList(@RequestParam("workOrderId") String workOrderId) {
        Result result = Result.error();
        if (StringUtils.isEmpty(workOrderId)) {
            return result.message("稽核数据主键不能为空");
        } else {
            Map<String, Object> resultMap = new HashMap<>();
            List<AuditExitEntrDTO> auditExitEntrList = iAuditConclusionService.doFindEntryAndExitList(workOrderId);
            resultMap.put("result", auditExitEntrList);
            result = Result.ok().data("resultData", resultMap);
        }
        return result;
    }

    @ApiModelProperty("外部稽核-稽核数据清单-门架数据")
    @GetMapping("/doFindAuditTrans")
    public Result doFindAuditTrans(AuditTransQuery auditTransQuery) {
        Map<String, Object> dataMap = new HashMap<>();
        if (StringUtils.isEmpty(auditTransQuery.getPageIndex())) {
            auditTransQuery.setPageIndex(1);
        }
        if (StringUtils.isEmpty(auditTransQuery.getPageSize())) {
            auditTransQuery.setPageSize(10);
        }
        PageInfo<AuditTransDTO> auditTransDtoageInfo = iAuditConclusionService.doFindAuditTrans(auditTransQuery);
        dataMap.put("pageInfo", auditTransDtoageInfo);
        return Result.ok().data("resultData", dataMap);
    }

    @ApiOperation("外部稽核-稽核数据清单-发行方稽核结论查询")
    @GetMapping("/doFindIssuerAuditConclusion")
    public Result doFindIssuerAuditConclusion(@RequestParam("workOrderId") String workOrderId) {
        Result result = Result.error();
        if (StringUtils.isEmpty(workOrderId)) {
            return result.message("稽核数据主键不能为空");
        } else {
            Map<String, Object> resultMap = new HashMap<>();
            AuditIssuerConclusionDTO auditIssuerConclusionDto = iAuditConclusionService.doFindIssuerAuditConclusion(workOrderId);
            resultMap.put("result", auditIssuerConclusionDto);
            result = Result.ok().data("resultData", resultMap);
        }
        return result;

    }

    @ApiOperation("外部稽核-待发起（省中心退回）& 代派发（省中心）")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "Date"),
		@ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Date") ,
		@ApiImplicitParam(name = "pageIndex", value = "当前页码", dataType = "Integer") ,
		@ApiImplicitParam(name = "pageSize", value = "当前页码显示条数", dataType = "Integer") })
    @RequestMapping(method = RequestMethod.POST, value ="/doFindWorkorder")
    public Result doFindWorkorder(AuditWorkOrderTableEntity table,Date startTime, Date endTime,
			Integer pageIndex,Integer pageSize) {
    	List<AuditWorkOrderTableEntity> tableList = null;
		try {
			tableList = iAuditConclusionService.doFindWorkorder(table,startTime,endTime,pageIndex,pageSize);
		} catch (BreakRulesException e) {
			log.error(e.getMsg());
			e.printStackTrace();
			return Result.error(e.getCode(),e.getMsg());
	    } catch (ParseException e) {
	    	log.error("起始时间格式不正常，原因："+e.getMessage());
	    	e.printStackTrace();
	    	return Result.error(ResultCode.DATA_ERROR,"起始时间格式不正常");
	    }
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("AuditWorkOrderTableEntity", tableList);
		Result result = Result.ok().data(map);
    	return result;
    }

    @ApiOperation("外部稽核-稽核数据清单-路段稽核结论查询")
    @GetMapping("/doFindRoadAuditConclusion")
    public Result doFindRoadAuditConclusion(AuditRoadConclusionQuery auditRoadConclusionQuery) {
        Map<String, Object> dataMap = new HashMap<>();
        List<AuditRoadConclusionDTO> auditRoadConclusionDtos = iAuditConclusionService.doFindRoadAuditConclusion(auditRoadConclusionQuery);
        dataMap.put("result", auditRoadConclusionDtos);
        return Result.ok().data("resultData", dataMap);
    }


    @ApiOperation("外部稽核-稽核数据清单-协查方")
    @GetMapping("/doFindCoOperativeParty")
    public Result doFindCoOperativeParty(@RequestParam("checkCommentsId") String checkCommentsId) {
        Result result = Result.error();
        if (StringUtils.isEmpty(checkCommentsId)) {
            result = Result.error().message("稽核结论主键不能为空");
        } else {
            Map<String, Object> resultMap = new HashMap<>();
            AuditUnitDTO auditUnitDto = iAuditConclusionService.doFindAuditUnitById(checkCommentsId);
            resultMap.put("result", auditUnitDto);
            result = Result.ok().data("resultData", resultMap);
        }
        return result;
    }

    @ApiOperation("外部稽核-创建稽查工单-提交")
    @PostMapping("/doCreateWorkOrder")
    public Result doCreateWorkOrder(@RequestBody AuditAuditorListDTO auditAuditorListDto) {
        Result result = Result.error();
        if (null == auditAuditorListDto) {
            return result.message("提交的稽查工单不能为空");
        }
        if (1 == auditAuditorListDto.getRmFlag() && StringUtils.isEmpty(auditAuditorListDto.getWorkOrderId())) {
            return result.message("提交的稽核工单内容出错");
        }
        result = iAuditConclusionService.doCreateWorkOrder(auditAuditorListDto);
        return result;
    }

    @ApiOperation("外部稽核-代派发列表（省中心）")
    @ApiImplicitParam(name = "dataNumber", value = "工单编号", dataType = "String")
    @RequestMapping(method = RequestMethod.POST, value ="/doFindProvincialCenter")
    public <T> Result doFindProvincialCenter(String dataNumber) {
    	Map<String, List<T>> resultMap = iAuditConclusionService.doFindProvincialCenter(dataNumber);
    	if(!resultMap.isEmpty()) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		for (Entry<String, List<T>> entry : resultMap.entrySet()) {
    			map.put(entry.getKey(),entry.getValue());
			}
    		return Result.ok().data(map);
    	}
    	return Result.error();
    }

}









