package com.sutong.transflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.bjstjh.result.ConstClass;
import com.sutong.bjstjh.result.Result;
import com.sutong.transflow.service.AuditCollectQueryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AuditCollectQueryController
 * @Description
 * @Author: Mr.Su
 * @Date: 2020/1/5 15:17
 * @Version V1.0
 **/
@RestController
@CrossOrigin
public class AuditCollectQueryController  {
    @Reference
    private AuditCollectQueryService auditCollectQueryService;
    /*
     * @description: 汇总结果查询分页
     * @auther: Mr.Su
     * @date: 2020/1/6 10:09
     * @Param payEndInformModel:结果实体
     * @Param pageNum:当前页码
     * @Param pageSize:分页条数
     * @return: Result
     **/
    @GetMapping("/auditPayEnd/yearPage")
    public Result doFindPayEndInformYearPage(@RequestParam(value = "payEndTime",required = false)String  payEndTime){
        List<Map<String,Object>> infoList = auditCollectQueryService.getYearCount(payEndTime);
        if (null==infoList||infoList.isEmpty()){
            return Result.error().message(ConstClass.DATE_IS_NULL);
        }
        infoList=auditCollectQueryService.sumTotalPay(infoList);
        return Result.ok().data("pageInfo",infoList);
    }

    @GetMapping("/auditPayEnd/monthPage")
    public Result doFindPayEndInformMonthPage(@RequestParam(value = "payEndTime",required = false)String  payEndTime){
        List<Map<String,Object>> infoList = auditCollectQueryService.getMonthCount(payEndTime);
        if (null==infoList||infoList.isEmpty()){
            return Result.error().message(ConstClass.DATE_IS_NULL);
        }
        infoList=auditCollectQueryService.sumTotalPay(infoList);
        return Result.ok().data("pageInfo",infoList);
    }
}
