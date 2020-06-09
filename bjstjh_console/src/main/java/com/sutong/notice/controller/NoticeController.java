package com.sutong.notice.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.Result;
import com.sutong.notice.service.NoticeService;
import com.sutong.runfee.model.RunFee;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class NoticeController {

    @Reference
    private NoticeService noticeService;

    //绑定变量名字和属性，参数封装进类
    @InitBinder("runFee")
    public void initBinderRunFee(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("runFee.");
    }


    /*
     * @description: 新建客户补费通知信息
     * @auther: Mr.Su
     * @date: 2019/12/15 13:54
     * @Param runFee:
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/notice/insert")
    public Result doInsertNotice(@ModelAttribute("runFee")RunFee runFee){
        noticeService.doInsertNoticeSelective(runFee);
        return Result.ok();
    }

    /*
     * @description: 查询客户补费通知列表分页
     * @auther: Mr.Su
     * @date: 2019/12/19 14:46
     * @Param runFee:
     * @Param pageNum:
     * @Param pageSize:
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/notice/page")
    public Result doFindRunFeePage(@ModelAttribute("runFee")RunFee runFee,
                                   @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                   @RequestParam(value = "pageSize",defaultValue = "10")int pageSize ){

        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageInfo<RunFee> pageInfo = noticeService.doFindRunFeePage(pageNum, pageSize, runFee);
        return Result.ok().data("pageInfo",pageInfo);
    }
    /*
     * @description: 查询客户补费通知列表（手动通知）
     * @auther: Mr.Su
     * @date: 2019/12/14 16:18

     * @return: com.sutong.bjstjh.result.Result
     **/

    @RequestMapping("/notice/list")
    public Result doFindRunFeeList(@ModelAttribute("runFee")RunFee runFee){
        List<RunFee> runFeeList = noticeService.doFindRunFeeList(runFee);
        return Result.ok().data("runFeeList",runFeeList);
    }

    /*
     * @description: 查询客户补费通知列表（自动通知）
     * @auther: Mr.Su
     * @date: 2019/12/14 16:18

     * @return: com.sutong.bjstjh.result.Result
     **/
    /*@RequestMapping("/noticeAuto/list")
    public Result doFindRunFeeAutoList(@ModelAttribute("runFee")RunFee runFee){
        List<RunFee> runFeeAutoList = noticeService.doFindRunFeeAutoList(runFee);
        return Result.ok().data("runFeeAutoList",runFeeAutoList);
    }*/

}
