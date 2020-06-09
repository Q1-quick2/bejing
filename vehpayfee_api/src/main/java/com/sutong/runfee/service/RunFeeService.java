/**
 * @Description: 客户逃费管理
 * @ClassName: RunFeeService
 * @author： Mr.Kong
 * @date: 2019/12/13 16:39
 * @Version： 1.0
 */
package com.sutong.runfee.service;

import com.github.pagehelper.PageInfo;
import com.sutong.runfee.model.RunFee;

import java.util.List;
public interface RunFeeService {
    /**
     * @description: 更新补费状态
     * @auther: Mr.kong
     * @date: 2019/12/19 19:56
     * @Param orderId:
     * @return: int
     **/
    int doUpdatePaybackStatus(String orderId);

    /**
     * @description: 新建客户逃费信息
     * @auther: Mr.kong
     * @date: 2019/12/14 16:59
     * @Param runFee: 客户逃费实体
     * @return: int
     **/
    int doInsertRunFeeSelective(RunFee runFee);
    /**
     * @description: 新建客户逃费信息
     * @auther: Mr.kong
     * @date: 2019/12/14 16:59
     * @Param runFee: 客户逃费实体
     * @return: int
     **/
    int doInsertRunFee(RunFee runFee);
    /**
     * @description: 查询客户逃费分页
     * @auther: Mr.kong
     * @date: 2019/12/14 15:09
     * @Param runFee:客户逃费实体
     * @Param pageNum:当前页码
     * @Param pageSize:分页条数
     * @return: PageInfo
     **/
    PageInfo<RunFee> doFindRunFeePage(int pageNum, int pageSize, RunFee runFee);

    /**
     * @description: 查询客户逃费列表
     * @auther: Mr.Wind
     * @date: 2019/12/13 17:11
     * @Param runFee: 客户逃费实体
     * @return: java.util.List<RunFee>
     **/
    List<RunFee> doFindRunFeeList(RunFee runFee);

    /**
     * @description: 查询客户逃费详情
     * @auther: Mr.kong
     * @date: 2019/12/14 10:59
     * @Param orderId: 工单编号
     * @return: RunFee
     **/
    RunFee doFindRunFeeInfo(Integer orderId);

    /*
     * @description: 更新逃费状态通知
     * @auther: Mr.Su
     * @date: 2019/12/22 18:45
     * @Param orderId:
     * @return: int
     **/
    int doUpdateSendSmsStatus(String orderId);
}
