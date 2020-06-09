package com.sutong.feeRecord.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.feeRecord.model.SupplementFeeRecordModel;
import com.sutong.feeRecord.service.SupplementFeeRecordService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SupplementFeeRecordController
 * @Description 补费流水查询
 * @Author ly
 * @Date 2019/12/14 18:04
 */
@RestController
@CrossOrigin
public class SupplementFeeRecordController {

    @Reference
    private SupplementFeeRecordService supplementFeeRecordService;
    // 绑定变量名字和属性，参数封装进类
    @InitBinder("feeRecord")
    public void initBinderSupplementFeeRecordModel(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("feeRecord.");
    }

    /**
     * @description: 查询客户补费流水分页
     * @auther: Mr.ly
     * @date: 2019/12/19 17:34
     * @Param supplementFeeRecord:补费流水查询实体类
     * @Param pageNum:当前页码
     * @Param pageSize:分页条数
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/feeRecord/page")
    public Result searchFeeRecord(@ModelAttribute("feeRecord") SupplementFeeRecordModel feeRecord,
                                  @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                  @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        PageInfo<SupplementFeeRecordModel> pageInfo = supplementFeeRecordService.doSearchFeeRecord(pageNum, pageSize,feeRecord);
        return Result.ok().data("pageInfo",pageInfo);
    }

    /**
     * @description: 查询客户补费流水详情
     * @auther: Mr.kong
     * @date: 2019/12/19 21:38
     * @Param orderNo:交易流水号
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/feeRecord/info")
    public Result searchFeeRecordAll(@RequestParam("id") String id){
        SupplementFeeRecordModel FeeRecord = supplementFeeRecordService.doFindFeeRecordInfo(id);
        return Result.ok().data("feeRecord",FeeRecord);
    }
}