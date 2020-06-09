package com.sutong.history.mapper;

import com.sutong.history.model.PaidHistoryModel;
import com.sutong.history.model.PastHistoryModel;
import com.sutong.history.model.PastInfoHistoryModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UploadHistoryMapper
 * @Description 历史数据上传
 * @Author ly
 * @Date 2019/12/13 16:36
 */
@Repository
@Mapper
@Component
public interface UploadHistoryMapper {


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
     * @date: 2019/12/22 13:57
     * @Param paidHistoryModel: 历史补费实体
     * @return: java.util.List<com.sutong.history.model.PaidHistoryModel>
     **/
    List<PaidHistoryModel> doFindPayHistoryPage(PaidHistoryModel paidHistoryModel);

    //补费历史导入
    int doInsertPaidList(PaidHistoryModel paidHistoryModel);
    //历史欠费汇总表
    int doInsertPastList(List<PastHistoryModel> pastHistoryModel);
    //历史欠费详情导入
    int doInsertPastInfoList(List<PastInfoHistoryModel> pastInfoHistoryModel);

    //根据上传的序号查询汇总表是否有数据
    boolean doFindObuId(String obuId);

    //一个obu号不能有重复的交易时间
    boolean doFindTransTime(String obuId,String transTime);

}
