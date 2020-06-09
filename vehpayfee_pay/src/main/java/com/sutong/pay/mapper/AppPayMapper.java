package com.sutong.pay.mapper;

import com.sutong.bjstjh.entity.AuditPastOrderEntity;
import com.sutong.bjstjh.entity.pay.AppPayPreOrderResponseEntity;
import com.sutong.bjstjh.entity.pay.PaybackFeeFlowEntity;
import com.sutong.bjstjh.entity.pay.PaybackFeeFlowPastEntity;
import com.sutong.bjstjh.entity.pay.PaybackFeeFlowVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by 王磊 on 2019/12/15.
 */
@Repository
@Mapper
@Component
public interface AppPayMapper {

    //查询欠费金额（非历史逃费）
    Integer doFindArrearageMoney(String orderId);

    //保存支付平台APP支付预下单返回结果
    void doInsertAppPayPreOrderResult(AppPayPreOrderResponseEntity vo);

    //生成补费流水（非历史逃费）
    void doInsertRepairFeeWater(PaybackFeeFlowEntity vo);

    //查询欠费金额（历史逃费）
    String doFindPastArrearageMoney(String obuId);

    //查询通行费差额汇总表中的数据用来生成交易流水
    AuditPastOrderEntity doFindPastOrder(String obuId);

    //生成补费流水（历史逃费）
    void doInsertPastPayBackFeeWater(PaybackFeeFlowPastEntity vo);

    //通行费差额明细单插入数据（历史逃费）
    void doUpdateFlowAndFee(@Param("orderNo") String orderNo, @Param("obuId") String obuId);

    //查询非历史逃费补费状态
    String doFindOrderStatus(String orderId);

    //查询历史逃费补费状态
    String doFindOrderStatusPast(String obuId);

    //查询工单发起方省中心id、工单发起方省中心生成的文件id、通行标识、OBU号、欠费金额
    PaybackFeeFlowVo doFindDisposeAndPassInfo(String orderId);

    //单独保存一份流水号，用来防止流水号重复
    void doInsertPayBackFeeId(@Param("orderNo") String orderNo,@Param("flowCreateTime") String flowCreateTime);

}
