package com.sutong.feeRecord.mapper;

import com.sutong.feeRecord.model.SupplementFeeRecordModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName SupplementFeeRecordMapper
 * @Description 补费流水查询
 * @Author ly
 * @Date 2019/12/13 16:36
 */
@Repository
@Mapper
@Component
public interface SupplementFeeRecordMapper {

    List<SupplementFeeRecordModel> doSearchFeeRecordList(SupplementFeeRecordModel record);

    //详情
    SupplementFeeRecordModel doFindFeeRecordInfo(String id);
}
