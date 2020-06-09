package com.sutong.feeRecord.service;

import com.github.pagehelper.PageInfo;
import com.sutong.feeRecord.model.SupplementFeeRecordModel;

/**
 * @ClassName SupplementFeeRecordServiceImpl
 * @Description 补费流水查询
 * @Author ly
 * @Date 2019/12/14 17:59
 */
public interface SupplementFeeRecordService {

    PageInfo<SupplementFeeRecordModel> doSearchFeeRecord(int pageNum, int pageSize,SupplementFeeRecordModel record);
    /**
     * @description: 查询客户补费流水
     * @auther: Mr.kong
     * @date: 2019/12/19 21:40
     * @Param orderNo:
     * @return: java.util.List<com.sutong.feeRecord.model.SupplementFeeRecordModel>
     **/
    SupplementFeeRecordModel doFindFeeRecordInfo(String id);
}
