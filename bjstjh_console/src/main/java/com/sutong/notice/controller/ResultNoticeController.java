package com.sutong.notice.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.model.FeeResult;
import com.sutong.notice.service.ResultNoticeService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: ResultNoticeController
 * @Description
 * @Author: Mr.Su
 * @Date: 2019/12/16 17:59
 * @Version V1.0
 **/
@RestController
@CrossOrigin
public class ResultNoticeController {

    @Reference
    private ResultNoticeService resultNoticeService;

    //绑定变量名字和属性，参数封装进类
    @InitBinder("feeResult")
    public void initBinderFeeResult(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("feeResult.");
    }

    /*
     * @description: 新建客户补费结果
     * @auther: Mr.Su
     * @date: 2019/12/16 18:03
     * @Param feeResult:
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/notice/insert/result")
    public Result doInsertResultNotice(@ModelAttribute("feeResult")FeeResult feeResult){
        resultNoticeService.doInsertResultNoticeSelective(feeResult);
        return Result.ok();
    }

    /**
     * @description: 查询客户补费结果分页
     * @auther: Mr.Su
     * @date: 2019/12/19 19:13
     * @Param feeResult:
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/repairFeeResult/page")
    public Result doFindFeeResultList(@ModelAttribute("feeResult")FeeResult feeResult,
                                      @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                      @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        PageInfo<FeeResult> pageInfo = resultNoticeService.doFindFeeResultList(pageNum, pageSize, feeResult);
        return Result.ok().data("pageInfo",pageInfo);
    }

    /**
     * @description: 查询客户补费结果详情
     * @auther: Mr.kong
     * @date: 2019/12/19 20:19
     * @Param orderId:工单编号
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/repairFeeResult/info")
    public Result doFindFeeResultProfileData(@RequestParam(value ="orderId",required = false)String orderId){
        FeeResult feeResult = resultNoticeService.doFindFeeResultInfo(orderId);
        return Result.ok().data("feeResult",feeResult);
    }
}
