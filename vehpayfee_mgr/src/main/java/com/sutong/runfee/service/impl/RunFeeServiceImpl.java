/**
 * @Description: 客户逃费管理
 * @ClassName: RunFeeServiceImpl
 * @author： Mr.Kong
 * @date: 2019/12/13 16:40
 * @Version： 1.0
 */
package com.sutong.runfee.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.runfee.mapper.RunFeeMapper;
import com.sutong.runfee.model.RunFee;
import com.sutong.runfee.service.RunFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class RunFeeServiceImpl implements RunFeeService {

    @Autowired
    private RunFeeMapper runFeeMapper;
    /**
     * @description: 更新补费状态
     * @auther: Mr.kong
     * @date: 2019/12/19 19:56
     * @Param orderId: 工单编号
     * @return: int
     **/
    @Override
    public int doUpdatePaybackStatus(String orderId) {
        return runFeeMapper.doUpdatePaybackStatus(orderId);
    }

    /**
     * @description: 新建客户逃费信息
     * @auther: Mr.kong
     * @date: 2019/12/14 16:59
     * @Param runFee: 客户逃费实体
     * @return: int
     **/
    @Override
    public int doInsertRunFeeSelective(RunFee runFee) {
        return runFeeMapper.doInsertRunFeeSelective(runFee);
    }
    /**
     * @description: 新建客户逃费信息
     * @auther: Mr.kong
     * @date: 2019/12/14 16:59
     * @Param runFee: 客户逃费实体
     * @return: int
     **/
    @Override
    public int doInsertRunFee(RunFee runFee) {
        return runFeeMapper.doInsertRunFee(runFee);
    }
    /**
     * @description: 查询客户逃费分页
     * @auther: Mr.kong
     * @date: 2019/12/14 15:09
     * @Param runFee:客户逃费实体
     * @Param pageNum:当前页码
     * @Param pageSize:分页条数
     * @return: PageInfo
     **/
    @Override
    public PageInfo<RunFee> doFindRunFeePage(int pageNum, int pageSize, RunFee runFee){
        PageHelper.startPage(pageNum, pageSize);
        List<RunFee> runFeeList = runFeeMapper.doFindRunFeePage(runFee);
        PageInfo<RunFee> pageInfo=new PageInfo<>(runFeeList);
        return pageInfo;
    }
    /**
     * @description: 查询客户逃费列表
     * @auther: Mr.Wind
     * @date: 2019/12/13 17:11
     * @Param runFee:客户逃费实体
     * @return: java.util.List<RunFee>
     **/
    @Override
    public List<RunFee> doFindRunFeeList(RunFee runFee) {
        return runFeeMapper.doFindRunFeeList(runFee);
    }
    /**
     * @description: 查询客户逃费详情
     * @auther: Mr.kong
     * @date: 2019/12/14 10:59
     * @Param orderId: 工单编号
     * @return: RunFee
     **/
    @Override
    public RunFee doFindRunFeeInfo(Integer orderId) {
        return runFeeMapper.doFindRunFeeInfo(orderId);
    }

    /*
     * @description: 更新逃费通知状态
     * @auther: Mr.Su
     * @date: 2019/12/22 18:47
     * @Param orderId:
     * @return: int
     **/
    @Override
    public int doUpdateSendSmsStatus(String orderId) {
        return runFeeMapper.doUpdateSendSmsStatus(orderId);
    }
}
