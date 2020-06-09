package com.sutong.feeRecord.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.feeRecord.mapper.SupplementFeeRecordMapper;
import com.sutong.feeRecord.model.SupplementFeeRecordModel;
import com.sutong.feeRecord.service.SupplementFeeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName SupplementFeeRecordServiceImpl
 * @Description 补费流水查询
 * @Author ly
 * @Date 2019/12/14 17:55
 */
@Component
@Service
public class SupplementFeeRecordServiceImpl implements SupplementFeeRecordService {
    @Autowired
    SupplementFeeRecordMapper supplementFeeRecordMapper;
    @Override
    public PageInfo<SupplementFeeRecordModel> doSearchFeeRecord(int pageNum, int pageSize,SupplementFeeRecordModel record){
        PageHelper.startPage(pageNum,pageSize);

        List<SupplementFeeRecordModel> feeRecordModelList =supplementFeeRecordMapper.doSearchFeeRecordList(record);
        PageInfo<SupplementFeeRecordModel> pageInfo=new PageInfo<>(feeRecordModelList);
        return pageInfo;
    }

    //详情
    @Override
    public SupplementFeeRecordModel doFindFeeRecordInfo(String id){
        return supplementFeeRecordMapper.doFindFeeRecordInfo(id);
    }
}
