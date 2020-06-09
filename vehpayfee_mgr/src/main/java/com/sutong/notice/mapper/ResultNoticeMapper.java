package com.sutong.notice.mapper;

import com.sutong.model.FeeResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
@Component
public interface ResultNoticeMapper {
    /*
     * @description: 新增客户补费结果
     * @auther: Mr.Su
     * @date: 2019/12/16 18:26
     * @Param feeResult: 补费结果实体
     * @return: int
     **/
    int doInsertResultNotice(FeeResult feeResult);

    /*
     * @description: 新增客户补费结果
     * @auther: Mr.Su
     * @date: 2019/12/16 18:27
     * @Param feeResult: 补费结果实体
     * @return: int
     **/
    int doInsertResultNoticeSelective(FeeResult feeResult);

    /*
     * @description: 查询客户补费结果
     * @auther: Mr.Su
     * @date: 2019/12/16 18:27
     * @Param feeResult: 补费结果实体
     * @return: java.util.List<com.sutong.model.FeeResult>
     **/
    List<FeeResult> doFindFeeResultList(FeeResult feeResult);
    /**
     * @description: 查询客户补费结果详情
     * @auther: Mr.kong
     * @date: 2019/12/19 20:20
     * @Param orderId: 工单编号
     * @return: com.sutong.model.FeeResult
     **/
    FeeResult doFindFeeResultInfo(String orderId);

    /**
     * @description: 查询客户补费结果详情
     * @auther: liyan
     * @date: 2019/12/19 20:20
     * @Param orderId: 工单编号
     * @return: com.sutong.model.FeeResult
     **/
    int doUpdateFeeStatus(String orderId);
}
