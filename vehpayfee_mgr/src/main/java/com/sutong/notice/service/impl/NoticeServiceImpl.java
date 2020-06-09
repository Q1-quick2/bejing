package com.sutong.notice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.mapper.NoticeMapper;
import com.sutong.notice.service.NoticeService;
import com.sutong.runfee.model.RunFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class NoticeServiceImpl implements NoticeService{
   @Autowired
   private NoticeMapper noticeMapper;

   /*
    * @description: 新建客户逃费通知信息
    * @auther: Mr.Su
    * @date: 2019/12/15 13:46
    * @Param runFee: 客户逃费实体
    * @return: int
    **/

    @Override
    public int doInsertNoticeSelective(RunFee runFee) {
        return noticeMapper.doInsertNoticeSelective(runFee);
    }

    /*
     * @description:
     * @auther: Mr.Su
     * @date: 2019/12/15 13:47
     * @Param runFee: 客户逃费实体
     * @return: int
     **/
    @Override
    public int doInsertNotice(RunFee runFee) {
        return noticeMapper.doInsertNotice(runFee);
    }

    /**
     * @ClassName: NoticeServiceImpl
     * @Description 查询客户逃费通知列表
     * @Author: Mr.Su
     * @Date: 2019/12/14 13:38
     * @Version V1.0
     **/

    @Override
    public List<RunFee> doFindRunFeeList(RunFee runFee) {
        return noticeMapper.doFindRunFeeList(runFee);
    }

    @Override
    public List<RunFee> doFindRunFeeAutoList(RunFee runFee) {
        return noticeMapper.doFindRunFeeAutoList(runFee);
    }

    @Override
    public PageInfo<RunFee> doFindRunFeePage(int pageNum, int pageSize, RunFee runFee) {
        PageHelper.startPage(pageNum, pageSize);
        List<RunFee> runFeeList = noticeMapper.doFindRunFeePage(runFee);
        PageInfo<RunFee> pageInfo=new PageInfo<>(runFeeList);
        return pageInfo;
    }

}
