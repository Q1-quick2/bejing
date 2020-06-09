package com.sutong.mapper;


import com.sutong.runfee.model.RunFee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NoticeMapper {
    /*
     * @description:
     * @auther: Mr.Su
     * @date: 2019/12/15 13:49
     * @Param runFee: 客户逃费实体
     * @return: int
     **/
    int doInsertNoticeSelective(RunFee runFee);

    /*
     * @description:
     * @auther: Mr.Su
     * @date: 2019/12/15 13:49
     * @Param runFee: 客户逃费实体
     * @return: int
     **/
    int doInsertNotice(RunFee runFee);


    /*
     * @description: 查询客户逃费通知列表
     * @auther: Mr.Su
     * @date: 2019/12/15 10:55
     * @Param runFee: 客户逃费实体
     * @return: java.util.List<com.sutong.runfee.model.RunFee>
     **/
    List<RunFee> doFindRunFeeList(RunFee runFee);


    List<RunFee> doFindRunFeeAutoList(RunFee runFee);

    List<RunFee> doFindRunFeePage(RunFee runFee);
}
