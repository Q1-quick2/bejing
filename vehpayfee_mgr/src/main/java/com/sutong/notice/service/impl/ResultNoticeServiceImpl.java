package com.sutong.notice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.model.FeeResult;
import com.sutong.notice.mapper.ResultNoticeMapper;
import com.sutong.notice.service.ResultNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: ResultNoticeServiceImpl
 * @Description
 * @Author: Mr.Su
 * @Date: 2019/12/16 18:18
 * @Version V1.0
 **/
@Component
@Service
public class ResultNoticeServiceImpl implements ResultNoticeService{
    @Autowired
    private ResultNoticeMapper resultNoticeMapper;

    @Override
    public int doInsertResultNotice(FeeResult feeResult) {
        return resultNoticeMapper.doInsertResultNotice(feeResult);
    }

    @Override
    public int doInsertResultNoticeSelective(FeeResult feeResult) {
        return resultNoticeMapper.doInsertResultNoticeSelective(feeResult);
    }

    @Override
    public PageInfo<FeeResult> doFindFeeResultList(int pageNum, int pageSize, FeeResult feeResult) {
        PageHelper.startPage(pageNum, pageSize);
        List<FeeResult> runFeeList = resultNoticeMapper.doFindFeeResultList(feeResult);
        PageInfo<FeeResult> pageInfo=new PageInfo<>(runFeeList);
        return pageInfo;
    }
    /**
     * @description: 查询客户补费结果详情
     * @auther: Mr.kong
     * @date: 2019/12/19 20:20
     * @Param orderId: 工单编号
     * @return: com.sutong.model.FeeResult
     **/
    @Override
    public FeeResult doFindFeeResultInfo(String orderId) {
        return resultNoticeMapper.doFindFeeResultInfo(orderId);
    }

    /**
     * @description: 补费管理-客户补费通知成功后修改数据库状态
     * @auther: liyan
     * @date: 2019/12/22 20:20
     * @Param orderId: 工单编号
     * @return: com.sutong.model.FeeResult
     **/
    @Override
    public boolean doUpdateFeeStatus(String orderId) {
        boolean result;
        try {
            int count = resultNoticeMapper.doUpdateFeeStatus(orderId);
            if(count != 0) {
                result = true;
            }else {
                result = false;
            }
        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
