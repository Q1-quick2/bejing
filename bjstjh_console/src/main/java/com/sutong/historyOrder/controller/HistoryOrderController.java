package com.sutong.historyOrder.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.entity.AuditDictionaryInfoTable;
import com.sutong.bjstjh.entity.AuditWorkOrderHistoryTable;
import com.sutong.bjstjh.enumerate.DictEnum;
import com.sutong.bjstjh.exception.BreakRulesException;
import com.sutong.bjstjh.exception.DataStaleException;
import com.sutong.bjstjh.exception.FilesUploadException;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.bjstjh.util.CollectionUtils;
import com.sutong.bjstjh.util.FileUtils;
import com.sutong.bjstjh.util.POIUtils;
import com.sutong.bjstjh.util.StringUtils;
import com.sutong.bjstjh.util.enumerate.FileConfigEnum;
import com.sutong.bjstjh.util.enumerate.TableColumnEnum;
import com.sutong.historyOrder.model.ParameterHistoryOrder;
import com.sutong.historyOrder.service.HistoryOrderService;
import com.sutong.service.IDictService;
import com.sutong.service.IExcelService;
import com.sutong.service.IFileProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 常规外查工单
 * @author Administrator
 */

@CrossOrigin
@EnableSwagger2
@RestController
@Api(value = "HistoryOrderController ", tags = "常规外查历史工单接口")
public class HistoryOrderController {
    private static final Logger log = LoggerFactory.getLogger(HistoryOrderController.class);

    @Reference
    private HistoryOrderService historyOrderService;

    @Reference
    private IDictService dictService;

    @Reference
    private IFileProcessService fileProcessService;

    @Reference
    private IExcelService excelService;

    /**
     * 根据前台条件信息查询常规外查工单
     *
     * @param parameterHistoryOrder
     * @return
     */

    @ApiOperation("历史工单管理-查询常规外查工单表")
    @GetMapping("/historyOrder")
    public Result doFindOrder(@ModelAttribute ParameterHistoryOrder parameterHistoryOrder) {
        Map<String, Object> resultMap = new HashMap<>();
        if (org.springframework.util.StringUtils.isEmpty(parameterHistoryOrder.getPageIndex())) {
            parameterHistoryOrder.setPageIndex(1);
        }
        if (org.springframework.util.StringUtils.isEmpty(parameterHistoryOrder.getPageSize())) {
            parameterHistoryOrder.setPageSize(10);
        }
        PageInfo<AuditWorkOrderHistoryTable> FindOrder = historyOrderService.doFindOrder(parameterHistoryOrder);

        resultMap.put("pageInfo", FindOrder);

        return Result.ok().data("resultMap", resultMap);
    }

    /**
     * 	创建常规外查工单表
     *
     * @param multipartFile
     * @param auditWorkOrderHistoryTable
     * @return
     */
    @ApiOperation("历史工单管理-创建常规外查工单表")
    @PostMapping("/doInsertOrder")
    public Result doInsertOrder(@RequestParam(value = "file", required = false) MultipartFile[] multipartFile,
                                @ModelAttribute AuditWorkOrderHistoryTable auditWorkOrderHistoryTable) {
        //给主键id赋值
        String tableID = StringUtils.generateUUID();
        auditWorkOrderHistoryTable.setSerialId(tableID);
        if (multipartFile != null) {
            for (MultipartFile file : multipartFile) {
                try {
                    //调用pwz的上传证据的方法FileUtils.upload2
                    Map<String, String> upload2 = FileUtils.upload2(file, FileConfigEnum.AUDIT_WORKORDER_HISTORY_TABLE,
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
        historyOrderService.doInsertOrder(auditWorkOrderHistoryTable);
        //返回结果
        return Result.ok();
    }

    /**
     *	 导入常规外查工单表
     *
     * @param excelFile
     * @return
     */
    @ApiOperation("历史工单管理-导入常规外查工单表")
    @PostMapping("/doInsertTable")
    public Result doInsertTable(@RequestParam("excelFile") MultipartFile excelFile) {
    	try {
    		boolean isException = false;
    		String errMsg = "错误行数：${err_total_row}行（外查单号唯一性错误的行号有：${err_row}） ；" ; 
    		Map<String, List<Object>> caseExcelMap = POIUtils.caseExcelMap(excelFile);
    		List<String> rowNumber = CollectionUtils.getRowNumber(2, caseExcelMap.values());
    		List<String> rowList = new ArrayList<String>();
    		List<Object> list = caseExcelMap.get("外查单号");
    		if(list == null)
    			throw new BreakRulesException(ResultCode.DATA_ERROR, 
    					"在Excel文件中未检测到<外查单号>列,检查列头是否位于第一行");
    		checkField(list, rowNumber, rowList, isException);
    		if(isException) {
    			errMsg = errMsg.replace("${err_total_row}", rowList.size()+"");
    			errMsg = errMsg.replace("${err_row}", rowList+"");
    			throw new DataStaleException(ResultCode.DATA_ERROR, errMsg);
    		}
    		//校验obu唯一性
    		List<Object> obuList = caseExcelMap.get("标签号");
    		if(obuList == null)
    			throw new BreakRulesException(ResultCode.DATA_ERROR, 
    					"在Excel文件中未检测到<标签号>列,检查列头是否位于第一行");
    		checkField(obuList, rowNumber, rowList, isException);
    			if(isException) {
    				throw new DataStaleException(ResultCode.DATA_ERROR,
    						"错误行数："+ rowList.size()+"行（标签号唯一性错误的行号有："+rowList+"） ；");
    			}
//    		}
    		List<Object> sumPayList = caseExcelMap.get("补款金额(元)");
    		if(sumPayList == null)
    			throw new BreakRulesException(ResultCode.DATA_ERROR, 
    					"在Excel文件中未检测到<补款金额(元)>列,检查列头是否位于第一行");
    		for (Object object : sumPayList) {
    			Double valueOf = Double.valueOf(String.valueOf(object));
				if(valueOf < 0) {
					List<Integer> indexOfAll = CollectionUtils.indexOfAll(object, sumPayList);
					for (int i : indexOfAll) {
						rowList.add(rowNumber.get(i));
						isException = true;
					}
				}
			}
    		if(isException) {
				throw new DataStaleException(ResultCode.DATA_ERROR,
						"错误行数："+ rowList.size()+"行（补款金额(元) 错误的行号有："+rowList+"） ；");
			}
    		List<AuditWorkOrderHistoryTable> setFileds =
    				excelService.setFileds(new AuditWorkOrderHistoryTable(), caseExcelMap, IExcelService.StrictState);
    		//先查询是否有重复导入的ID
    		List<AuditWorkOrderHistoryTable> batchById = historyOrderService.doFindBatchById(list);
    		//如果不为空,说明数据已经存在
//    		if(batchById != null && batchById.size() != 0) {
    			//获取行数
    			List<String> idList = batchById.stream().
    					map(AuditWorkOrderHistoryTable :: getExternalOrderNo).distinct()
    					.collect(Collectors.toList());
    			for (String string : idList) {
    				List<Integer> indexOfAll = CollectionUtils.indexOfAll(string, list);
    				//根据下标取行数
    				for (int i : indexOfAll) {
    					String row = rowNumber.get(i);
    					rowList.add(row);
    					isException = true;
    				}
    			}
    			if(isException) {
    				errMsg = errMsg.replace("${err_total_row}", batchById.size()+"");
    				errMsg = errMsg.replace("${err_row}", rowList+"");
    				throw new DataStaleException(ResultCode.DATA_ERROR, errMsg);
    			}
//    		}
    		//去数据库查询是否有重复的obu'号
    		List<AuditWorkOrderHistoryTable> obuTableList = historyOrderService.doFindBatchByObuId(obuList);
    		List<String> obuBatchList = obuTableList.stream().
					map(AuditWorkOrderHistoryTable :: getObuno).distinct()
					.collect(Collectors.toList());
			for (String string : obuBatchList) {
				List<Integer> indexOfAll = CollectionUtils.indexOfAll(string, obuList);
				//根据下标取行数
				for (int i : indexOfAll) {
					String row = rowNumber.get(i);
					rowList.add(row);
					isException = true;
				}
			}
			if(isException) {
				throw new DataStaleException(ResultCode.DATA_ERROR, 
						"错误行数："+ rowList.size()+"行（标签号唯一性错误的行号有："+rowList+"） ；");
			}
    		//查询责任归属字典
    		List<AuditDictionaryInfoTable> dictVOList =
    				dictService.queryDictByDictType(DictEnum.DICT_DUTY_BELONG.toString().toUpperCase());
    		Map<String, Integer> map = new HashMap<String, Integer>();
    		for (AuditDictionaryInfoTable dictionary : dictVOList) {
    			map.put(dictionary.getDictName(), dictionary.getDictNumber());
    		}
    		for (AuditWorkOrderHistoryTable tableVO : setFileds) {
    			Integer belongBak = matchDutyBelongBak(tableVO.getDutyBelongBak(), map);
    			tableVO.setDutyBelong(belongBak);
    			//导入的金额单位是元,转为分后入库
    			Double sumPay = tableVO.getSumPay();
    			if(sumPay != null && sumPay != 0.0) {
    				if(String.valueOf(sumPay).contains(".")) {
    					String[] split = String.valueOf(sumPay).split("\\.");
    					//如果小数点右边长度大于2,抛出异常
    					if(split.length > 2) {
    						throw new DataStaleException(ResultCode.DATA_ERROR, "输入了错误的数值，错误值："+sumPay);
    					}
    				}
    		        BigDecimal yuan = new BigDecimal(sumPay);
    		        BigDecimal divide = yuan.multiply(new BigDecimal(100));
    		        BigDecimal newNumber = divide.setScale(0, BigDecimal.ROUND_HALF_UP);
    		        tableVO.setSumPay(Double.valueOf(newNumber.toString()));
    			}
    		}
    		historyOrderService.doInsertTable(setFileds);
    		return Result.ok();
    	} catch (DataStaleException e) {
    		log.error(e.getMsg());
    		e.printStackTrace();
    		return Result.error(ResultCode.ERROR, e.getMsg());
    	} catch (BreakRulesException e) {
    		log.error(e.getMsg());
    		e.printStackTrace();
    		return Result.error(ResultCode.ERROR, e.getMsg());
    	} catch (Exception e) {
    		log.error(e.getMessage());
    		e.printStackTrace();
    		if(e instanceof NumberFormatException) {
    			if(e.getMessage().equals("empty String")) {
    				String err = "数值项输入了非数值内容，错误值："+ " ;输入了空值";
    				return Result.error(ResultCode.ERROR, err);
    			}
    			String message = e.getMessage();
    			int start = message.indexOf("\"");
    			int end = message.lastIndexOf("\"");
    			message = message.substring(start+1,end);
    			String err = "数值项输入了非数值内容，错误值："+ message;
    			return Result.error(ResultCode.ERROR, err);
    		}
    		if(e instanceof Exception) {
    			String message = e.getMessage();
    			if(message.contains("BaseException")) {
    				String suffix = "BaseException{message=";
        			int start = message.indexOf(suffix)+suffix.length();
        			int end = message.indexOf(", code=");
        			String substring = message.substring(start, end);
        			return Result.error(ResultCode.ERROR, substring);
    			}
    			return Result.error(ResultCode.ERROR, 
    					"导入失败: "+"\n"+
    					"1、值可能超出数据库字段长度限制"+"\n"+
    					"2、网络繁忙"+"\n"+
    					"3、服务器开小差了,请稍后再试");
    		}
    		return Result.error();
    	}
    }


    /**
     * @description: 查询常规外查工单详情
     * @return: com.sutong.bjstjh.result.Result
     **/
    @ApiOperation("查询常规外查工单详情表")
    @GetMapping("/doFindHistoryResultInfo")
    public Result doFindHistoryResultInfo(@RequestParam("serialId") String serialId) {
        Map<String, Object> resultMap = new HashMap<>();
        //查询常规外查工单详情
        AuditWorkOrderHistoryTable auditWorkOrderHistoryTable = historyOrderService.doFindHistoryResultInfoSerialId(serialId);

        //查询常规外查工单证据url
        /*List<AuditFile> auditEvidenceResults = historyEvidenceService.doFindEvidenceResultList(auditWorkOrderHistoryTable.getSerialId());*/
        //调用pwz的查询证据方法返回路径
        List<String> filepath = fileProcessService.queryFilepath(FileConfigEnum.AUDIT_WORKORDER_HISTORY_TABLE, serialId);

        resultMap.put("filepath", filepath);
        resultMap.put("auditPublishResult", auditWorkOrderHistoryTable);
        return Result.ok().data("resultMap", resultMap);
    }
    
    /**
     * 	修改常规外查补费状态
     *
     * @param serialId
     * @param status1
     * @return com.sutong.bjstjh.result.Result
     */

    @ApiOperation("常规外查补费状态")
    @PostMapping("/doupdate1/{serialId}/{status1}")
    public Result doupdate1(@PathVariable("serialId") String serialId, @PathVariable("status1") Integer status1) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("serialId", serialId);
        hashMap.put("status1", status1);
        Integer result = historyOrderService.doUpdate1(hashMap);
        if (result > 0) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
    
    /**
     * 	修改常规外查变更状态
     *
     * @param serialId
     * @param status2
     * @return com.sutong.bjstjh.result.Result
     */

    @ApiOperation("常规外查变更状态")
    @PostMapping("/doupdate2/{serialId}/{status2}")
    public Result doupdate2(@PathVariable("serialId") String serialId, @PathVariable("status2") Integer status2) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("serialId", serialId);
        hashMap.put("status2", status2);
        Integer result = historyOrderService.doUpdate2(hashMap);
        if (result > 0) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
    
    /**
     * 	查询追缴常规外查工单
     *
     * @param parameterHistoryOrder
     * @return: com.sutong.bjstjh.result.Result
     */
    @ApiOperation("查询追缴常规外查工单")
    @GetMapping("/doFindHistoryRecovered")
    public Result doFindHistoryRecovered(ParameterHistoryOrder parameterHistoryOrder) {
        //查询追缴常规外查工单
        Map<String, Object> resultMap = new HashMap<>();
        if (org.springframework.util.StringUtils.isEmpty(parameterHistoryOrder.getPageIndex())) {
            parameterHistoryOrder.setPageIndex(1);
        }
        if (org.springframework.util.StringUtils.isEmpty(parameterHistoryOrder.getPageSize())) {
            parameterHistoryOrder.setPageSize(10);
        }
        PageInfo<AuditWorkOrderHistoryTable> HistoryRecovered = historyOrderService.doFindHistoryRecovered(parameterHistoryOrder);
        resultMap.put("pageInfo", HistoryRecovered);
        return Result.ok().data("resultMap", resultMap);
    }
    
    /**
     * @param request
     * @return
     * @Description 常规外查工单责任归属统计
     * 	现有的责任归属方:
     * 1-银行责任
     * 2-业务员责任
     * 3-系统责任
     * 4-客户责任
     * 5-其他
     * @author pengwz
     * @date 2019年12月25日 下午4:57:45
     */
    @ApiOperation("常规外查工单责任归属统计")
    @PostMapping("/history/doFind/doFindTotals")
    public Result doFindTotals(HttpServletRequest request) {
        Map<String, Integer> historyTotal = historyOrderService.doFindHistoryTotal();
        return Result.ok().data("doFindTotals", historyTotal);
    }
    
    /**
     * 	对于责任的归属的特殊操作
     *
     * @param dutyBelongBak
     * @param
     * @return
     * @author pengwz
     * @date 2019年12月30日 下午5:52:47
     */
    private Integer matchDutyBelongBak(String dutyBelongBak, Map<String, Integer> map) {
        if (dutyBelongBak == null || dutyBelongBak.trim().equals(""))
            return null;
        Pattern compile = Pattern.compile("银行业务员|系统|无法判定|客户|银行|业务员|其他");
        Matcher matcher = compile.matcher(dutyBelongBak);
        List<String> matcherList = new ArrayList<String>();
        while (matcher.find()) {
            matcherList.add(matcher.group());
        }
        if (matcherList.isEmpty())
            throw new BreakRulesException(ResultCode.ERROR, "无法为‘责任归属’属性赋值，"
                    + "错误值:‘"+dutyBelongBak+"’");
        Set<String> keySet = map.keySet();
        for (String str : keySet) {
            //如果字符串匹配到多个,不管三七二十一,就取第一个
            boolean matches = str.contains(matcherList.get(0));
            if (matches)
                return map.get(str);
        }
        throw new BreakRulesException(ResultCode.ERROR, "无法为‘责任归属’属性赋值，"
        		  + "错误值:‘"+dutyBelongBak+"’");
    }
    /**
     * 	根据业务校验字段
     * @param list
     * @param rowNumber
     * @param rowList
     * @param isException
     * @return
     */
    private boolean checkField(List<Object> list,List<String> rowNumber,List<String> rowList,boolean isException) {
    	Map<Object, Long> obuMap = list.stream().
    			collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
    	//如果不等于,说明有重复的记录
    	//		if(obuMap.size() != obuList.size()) {
    	Set<Entry<Object,Long>> obuEntrySet = obuMap.entrySet();
    	for (Entry<Object, Long> entry : obuEntrySet) {
    		if(entry.getValue() == 1 && !(entry.getKey().toString().trim().equals(""))) {
    			continue;
    		} else {
    			List<Integer> indexOfAll = CollectionUtils.indexOfAll(entry.getKey(), list);
    			//根据下标取行数
    			for (int i : indexOfAll) {
    				String row = rowNumber.get(i);
    				rowList.add(row);
    				isException = true;
    			}
    		}
    	}
    	return false;
    }
}
