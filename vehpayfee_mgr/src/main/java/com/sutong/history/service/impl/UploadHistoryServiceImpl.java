package com.sutong.history.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.history.mapper.UploadHistoryMapper;
import com.sutong.history.model.PaidHistoryModel;
import com.sutong.history.model.PastHistoryModel;
import com.sutong.history.model.PastInfoHistoryModel;
import com.sutong.history.service.UploadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Component
@Service
public class UploadHistoryServiceImpl implements UploadHistoryService {

    @Autowired
    UploadHistoryMapper uploadHistoryMapper;

    @Override
    public int updateSendSMSStatus(Integer id) {
        return uploadHistoryMapper.updateSendSMSStatus(id);
    }

    /**
     * @description: 查询历史补费详情
     * @auther: Mr.kong
     * @date: 2019/12/22 13:58
     * @Param id: 主键ID
     * @return: com.sutong.history.model.PaidHistoryModel
     **/
    @Override
    public PaidHistoryModel doFindPayHistoryInfoById(Integer id) {
        return uploadHistoryMapper.doFindPayHistoryInfoById(id);
    }
    /**
     * @description: 查询历史补费分页
     * @auther: Mr.kong
     * @date: 2019/12/22 13:56
     * @Param pageNum:当前页码
     * @Param pageSize:分页条数
     * @Param paidHistoryModel: 历史补费实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.history.model.PaidHistoryModel>
     **/
    @Override
    public PageInfo<PaidHistoryModel> doFindPayHistoryPage(int pageNum, int pageSize, PaidHistoryModel paidHistoryModel) {
        PageHelper.startPage(pageNum,pageSize);
        List<PaidHistoryModel> paidHistoryModels = uploadHistoryMapper.doFindPayHistoryPage(paidHistoryModel);
        PageInfo<PaidHistoryModel> pageInfo=new PageInfo<>(paidHistoryModels);
        return pageInfo;
    }

    //历史补费
//    public boolean paid(List<PaidHistoryModel> paidHistoryModel){
//        boolean msg;
//        try {
//            for (PaidHistoryModel paid : paidHistoryModel) {
//                uploadHistoryMapper.doInsertPaidList(paid);
//            }
//            msg = true;
//        }catch (Exception e){
//            e.printStackTrace();
//            msg = false;
//        }
//        return msg;
//    }

    /**
     * @description: 逃费汇总和详情表数据插入
     * @auther: liyan
     * @date: 2020/1/6 14:21
     * @param: [pastHistoryModel, pastInfoHistoryModel]
     * @return: java.lang.String
     **/
    @Transactional(rollbackFor = Exception.class)
    public String past(List<PastHistoryModel> pastHistoryModel,List<PastInfoHistoryModel> pastInfoHistoryModel) {

        //先查询，有上传过的直接返回，不进行插入操作。
        for (PastHistoryModel past : pastHistoryModel) {
            boolean exist = uploadHistoryMapper.doFindObuId(past.getObuId());
            if (exist) {
                return "汇总单已上传过OBU号：" + past.getObuId()+"!";
            }
        }
        for (PastInfoHistoryModel past : pastInfoHistoryModel) {
            //先查询汇总表是否有该transtime,同一个obuid只能不能有重复的transtime
            boolean exist = uploadHistoryMapper.doFindTransTime(past.getTransObuId(),past.getTransTime());
            if(exist){
                return "明细单已上传过OBU号："+past.getTransObuId()+"交易时间为"+past.getTransTime()+"的数据！";
            }
        }
        try {
            //插入数据库
            if(pastHistoryModel!=null && !pastHistoryModel.isEmpty()) {
                uploadHistoryMapper.doInsertPastList(pastHistoryModel);
            }
            if(pastInfoHistoryModel!=null && !pastInfoHistoryModel.isEmpty()) {
                uploadHistoryMapper.doInsertPastInfoList(pastInfoHistoryModel);
            }
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "导入失败，汇总单不能上传重复的的OBU号，明细单同一个OBU号不能上传重复的交易时间！";
        }
    }

}
