package com.sutong.runfee.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.runfee.model.RunFee;
import com.sutong.runfee.service.RunFeeService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description: 客户逃费管理
 * @author： Mr.Kong
 * @date: 2019/12/13 16:42
 */
@RestController
@CrossOrigin
public class RunFeeController {

    @Reference
    private RunFeeService runFeeService;

    // 绑定变量名字和属性，参数封装进类
    @InitBinder("runFee")
    public void initBinderRunFee(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("runFee.");
    }

    /**
     * @description: 新建客户逃费信息
     * @auther: Mr.kong
     * @date: 2019/12/14 14:00
     * @Param runFee:客户逃费实体
     * @return: Result
     **/
    @RequestMapping("/runFee/insert")
    public Result doInsertRunFee(@ModelAttribute("runFee") RunFee runFee){
        runFeeService.doInsertRunFeeSelective(runFee);
        return Result.ok();
    }

    /**
     * @description: 查询客户逃费分页
     * @auther: Mr.kong
     * @date: 2019/12/14 15:09
     * @Param runFee:客户逃费实体
     * @Param pageNum:当前页码
     * @Param pageSize:分页条数
     * @return: Result
     **/
    @RequestMapping("/runFee/page")
    public Result doFindRunFeePage(@ModelAttribute("runFee")RunFee runFee,
                                   @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                   @RequestParam(value = "pageSize",defaultValue = "10")int pageSize ){

        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageInfo<RunFee> pageInfo = runFeeService.doFindRunFeePage(pageNum, pageSize, runFee);
        return Result.ok().data("pageInfo",pageInfo);
    }


    /**
     * @description: 查询客户逃费列表
     * @auther: Mr.kong
     * @date: 2019/12/15 13:42
     * @Param runFee:
     * @return: Result
     **/
    @RequestMapping("/runFee/list")
    public Result doFindRunFeeList(@ModelAttribute("runFee")RunFee runFee){
        List<RunFee> runFeeList = runFeeService.doFindRunFeeList(runFee);
        return Result.ok().data("runFeeList",runFeeList);
    }

    /**
     * @description: 查询客户逃费详情-数据概况
     * @auther: Mr.kong
     * @date: 2019/12/13 18:08
     * @Param orderId:工单编号
     * @return: Result
     **/
    @RequestMapping("/runFee/profile/data")
    public Result doFindRunFeeProfileData(@RequestParam("orderId")Integer orderId ){
        RunFee runFee = runFeeService.doFindRunFeeInfo(orderId);
        return Result.ok().data("runFee",runFee);
    }

    /**
     * @description: 查询客户逃费详情-出入口概况
     * @auther: Mr.kong
     * @date: 2019/12/13 18:08
     * @Param orderId:工单编号
     * @return: Result
     **/
    @RequestMapping("/runFee/entrance/profile")
    public Result doFindRunFeeEntranceProfile(@RequestParam("orderId")Integer orderId ){
        RunFee runFee = runFeeService.doFindRunFeeInfo(orderId);
        return Result.ok().data("runFee",runFee);
    }

    /**
     * @description: 查询客户逃费详情-门架数据
     * @auther: Mr.kong
     * @date: 2019/12/13 18:08
     * @Param orderId:工单编号
     * @return: Result
     **/
    @RequestMapping("/runFee/door/frame/data")
    public Result doFindRunFeeDoorFrameData(@ModelAttribute("runFee")RunFee runFee){
        List<RunFee> runFeeList = runFeeService.doFindRunFeeList(runFee);
        return Result.ok().data("runFeeList",runFeeList);
    }
}
