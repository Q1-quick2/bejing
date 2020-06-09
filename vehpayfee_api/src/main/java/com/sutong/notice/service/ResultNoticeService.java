package com.sutong.notice.service;

import com.github.pagehelper.PageInfo;
import com.sutong.model.FeeResult;

public interface ResultNoticeService {
    /*
     * @description: 新建客户补费结果
     * @auther: Mr.Su
     * @date: 2019/12/16 16:58
     * @Param feeResult: 客户补费结果实体
     * @return: int
     **/
    int doInsertResultNotice(FeeResult feeResult);

    /*
     * @description: 新建客户补费结果
     * @auther: Mr.Su
     * @date: 2019/12/16 16:59
     * @Param feeResult: 客户补费结果实体
     * @return: int
     **/
    int doInsertResultNoticeSelective(FeeResult feeResult);
    /*
     * @description: 查询客户补费结果
     * @auther: Mr.Su
     * @date: 2019/12/16 16:23
     * @Param feeResult: 客户补费结果实体
     * @return: java.util.List<com.sutong.model.FeeResult>
     **/
    PageInfo<FeeResult> doFindFeeResultList(int pageNum, int pageSize,FeeResult feeResult);

    /**
     * @description: 查询客户补费结果详情
     * @auther: Mr.kong
     * @date: 2019/12/19 20:20
     * @Param orderId: 工单编号
     * @return: com.sutong.model.FeeResult
     **/
    FeeResult doFindFeeResultInfo(String orderId);

    /**
     * @description: 补费管理-客户补费通知成功后修改数据库状态
     * @auther: liyan
     * @date: 2019/12/22 20:20
     * @Param orderId: 工单编号
     * @return: com.sutong.model.FeeResult
     **/
    boolean doUpdateFeeStatus(String orderId);
}
