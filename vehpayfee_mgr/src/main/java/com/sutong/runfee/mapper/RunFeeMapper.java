/**
 * @Description: 客户逃费管理
 * @ClassName: RunFeeMapper
 * @author： Mr.Kong
 * @date: 2019/12/13 15:56
 * @Version： 1.0
 */
package com.sutong.runfee.mapper;

import com.sutong.runfee.model.RunFee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
@Component
public interface RunFeeMapper {
    /**
     * @description: 更新补费状态
     * @auther: Mr.kong
     * @date: 2019/12/19 19:56
     * @Param orderId: 工单编号
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
     * @return: com.sutong.bjstjh.result.Result
     **/
    List<RunFee> doFindRunFeePage(RunFee runFee);
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
     * @description: 更新逃费通知状态
     * @auther: Mr.Su
     * @date: 2019/12/23 11:09
     * @Param orderId:
     * @return: int
     **/
    int doUpdateSendSmsStatus(String orderId);
}
