package com.sutong.dict.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.bjstjh.entity.AuditDictionaryInfoTable;
import com.sutong.bjstjh.entity.AuditStatusInfoTable;
import com.sutong.bjstjh.result.Result;
import com.sutong.service.IDictService;
import com.sutong.service.IStatusTypeService;
import com.sutong.workorder.model.AuditStatusDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DictController
 * @Description: 数据库字典相关控制器
 * @author: pengwz
 * @date: 2019年12月20日 下午1:51:42
 */
@EnableSwagger2
@CrossOrigin
@Controller
@RequestMapping("/dict")
@Api(value = "DictController", tags = "数据库字典查询接口")
public class DictController {

    private static final Logger log = LoggerFactory.getLogger(DictController.class);

    @Reference
    private IDictService dictService;
    @Reference
    private IStatusTypeService iStatusTypeService;

    /**
     * @param dictType 字典类型
     * @return
     * @Description 根据字典类型（字典表名）返回该类型下的所有数据
     * @author pengwz
     * @date 2019年12月20日 下午1:57:17
     */
    @ApiOperation("字典查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictType", value = "字典类型", dataType = "String"/* ,paramType = "header" */)
    })
    @RequestMapping(method = RequestMethod.GET, value = "/dictFind/doFindDictByDictType")
    @ResponseBody
    public Result doFindDictByDictType(String dictType) {
        dictType = dictType.toUpperCase();
        log.info("---------------当前查询的字典类型：[" + dictType + "]");
        if (dictType == null || dictType.isEmpty())
            return Result.error();
        List<AuditDictionaryInfoTable> dict = dictService.queryDictByDictType(dictType);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(dictType, dict);
        Result result = Result.ok();
        result.setData(map);
        return result;
    }

    @ApiOperation("二级状态查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "statusType", value = "字典类型", dataType = "String"/* ,paramType = "header" */)
    })
    @RequestMapping(method = RequestMethod.GET, value = "/dictFind/doFindStatusType")
    @ResponseBody
    public Result doFindStatusType(String statusType) {
        statusType = statusType.toUpperCase();
        log.info("---------------当前查询的字典类型：[" + statusType + "]");
        if (statusType == null || statusType.isEmpty()) {
            return Result.error();
        }
        Map<String, Object> resultMap = new HashMap<>();
        List<AuditStatusInfoTable> auditStatusInfoTables = iStatusTypeService.dofindAllStatus(statusType);
        resultMap.put("ResultMap", auditStatusInfoTables);
        return Result.ok().data("resultData", resultMap);
    }

    @ApiOperation("二级状态查询接口-指定字典类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "statusType", value = "字典类型", dataType = "String"/* ,paramType = "header" */)
    })
    @RequestMapping(method = RequestMethod.GET, value = "/dictFind/doFindStatusTypeByType")
    @ResponseBody
    public Result doFindStatusTypeByType(String statusType) {
        statusType = statusType.toUpperCase();
        log.info("---------------当前查询的字典类型：[" + statusType + "]");
        if (statusType == null || statusType.isEmpty()) {
            return Result.error();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        List<AuditStatusDTO> auditStatusDTOS = iStatusTypeService.dofindStatusByType(statusType);
        map.put(statusType, auditStatusDTOS);
        return Result.ok().data("resultData", map);
    }


}
















