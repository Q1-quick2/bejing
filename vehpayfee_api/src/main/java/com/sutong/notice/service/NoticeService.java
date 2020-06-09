package com.sutong.notice.service;


import com.github.pagehelper.PageInfo;
import com.sutong.runfee.model.RunFee;

import java.util.List;

public interface NoticeService {
    /*
     * @description:新建客户逃费通知信息
     * @auther: Mr.Su
     * @date: 2019/12/15 12:01
     * @Param null:
     * @return: int
     **/
    int doInsertNoticeSelective(RunFee runFee);

    /*
     * @description: 新建客户逃费通知信息
     * @auther: Mr.Su
     * @date: 2019/12/15 12:03
     * @Param null:
     * @return: int
     **/

    int doInsertNotice(RunFee runFee);

    /*
     * @description:查询客户逃费通知列表
     * @auther: Mr.Su
     * @date: 2019/12/14 16:18
     * @Param runFee:客户逃费实体
     * @return: java.util.List<com.sutong.model.RunFee>
     **/
    List<RunFee> doFindRunFeeList(RunFee runFee);


    List<RunFee> doFindRunFeeAutoList(RunFee runFee);

    PageInfo<RunFee> doFindRunFeePage(int pageNum, int pageSize, RunFee runFee);
}
