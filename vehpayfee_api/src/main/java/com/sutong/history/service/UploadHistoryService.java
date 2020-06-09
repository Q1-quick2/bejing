package com.sutong.history.service;

import com.github.pagehelper.PageInfo;
import com.sutong.history.model.PaidHistoryModel;
import com.sutong.history.model.PastHistoryModel;
import com.sutong.history.model.PastInfoHistoryModel;

import java.util.List;

public interface UploadHistoryService {

    int updateSendSMSStatus(Integer id);

    /**
     * @description: 查询历史补费详情
     * @auther: Mr.kong
     * @date: 2019/12/22 13:58
     * @Param id: 主键ID
     * @return: com.sutong.history.model.PaidHistoryModel
     **/
    PaidHistoryModel doFindPayHistoryInfoById(Integer id);

    /**
     * @description: 查询历史补费分页
     * @auther: Mr.kong
     * @date: 2019/12/22 13:56
     * @Param pageNum:当前页码
     * @Param pageSize:分页条数
     * @Param paidHistoryModel: 历史补费实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.history.model.PaidHistoryModel>
     **/
    PageInfo<PaidHistoryModel> doFindPayHistoryPage(int pageNum,int pageSize,PaidHistoryModel paidHistoryModel);

    //补费历史导入
//    boolean paid(List<PaidHistoryModel> paidHistoryModel);

    //历史逃费汇总和详情导入
    String past(List<PastHistoryModel> pastHistoryModel,List<PastInfoHistoryModel> pastInfoHistoryModel) throws Exception;

}
