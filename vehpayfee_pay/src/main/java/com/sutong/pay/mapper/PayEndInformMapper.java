package com.sutong.pay.mapper;

import com.sutong.bjstjh.entity.PayEndInformModel;
import com.sutong.bjstjh.entity.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by main on 2019/12/15.
 */
@Repository
@Mapper
public interface PayEndInformMapper {

    //支付结果通知
    void AppPayEndInform(PayEndInformModel payEndInformModel);

    //补费结果查询
    @Select("SELECT END_paySerialNo as paySerialNo,END_resultcode as resultcode,END_actpayamt as actpayamt,END_paymenttime as paymenttime FROM AUDIT_PAY_END WHERE END_paySerialNo = #{paySerialNo}")
    PayEndInformModel doFindRepairfeeEnd(@Param("paySerialNo") String paySerialNo);

    //支付结果返回
    PayEndInformModel doFindReturn(@Param("paySerialNo") String paySerialNo);

    //通过订单号获取流水表
    @Select("SELECT ORDER_ID as orderId FROM AUDIT_PAY_BACK_FEE_FLOW WHERE PAY_BACK_FEE_ID = #{paySerialNo}")
    List<String> doFindRepairfeeWater(@Param("paySerialNo") String paySerialNo);

    //通过流水表orderId修改状态
    @Update("UPDATE AUDIT_RUN_FEE_FLOW SET PAYBACK_STATUS = '1' WHERE ORDER_ID = #{orderId}")
    void AppUpdateStatus(@Param("orderId") String orderId);

    // 查询用户手机号给用户发送短信    非历史
    @Select("SELECT distinct PAY_BACK_USER AS userName,PAY_BACK_USER_PHONE AS phone,PAY_BACK_SUM AS money FROM AUDIT_PAY_BACK_FEE_FLOW WHERE PAY_BACK_FEE_ID =  #{paySerialNo}")
    UserModel doFindPhone(@Param("paySerialNo") String paySerialNo);

    // 获取 逃费时间
    @Select(" SELECT distinct PASS_REAL_OUT_TIMES as passRealOutTimes FROM AUDIT_VEHICLE_INFO WHERE ORDER_ID = #{orderId} ")
    String doFindEvasionTime(@Param("orderId") String orderId);

    // 获取 逃费行为
    @Select("SELECT distinct COMFIRMED_ESCAPE_TYPE AS comfirmedEscapeType FROM AUDIT_MAIN_RESULT WHERE ORDER_ID = #{orderId} ")
    String doFindEvasionBehavior(@Param("orderId") String orderId);

    // 判断是否历史
    @Select("SELECT distinct ORDER_TYPE FROM AUDIT_PAY_BACK_FEE_FLOW WHERE pay_Back_Fee_Id = #{paySerialNo} ")
    List<String> doFindHistory(@Param("paySerialNo") String paySerialNo);

    // 查询用户手机号给用户发送短信    历史
    @Select("SELECT distinct PAY_BACK_USER AS userName,PAY_BACK_USER_PHONE AS phone,PAY_BACK_SUM AS money FROM AUDIT_PAY_BACK_FEE_FLOW_PAST WHERE PAY_BACK_FEE_ID =  #{paySerialNo}")
    UserModel doFindPhoneHistory(String paySerialNo);

    // 查Obu修改状态
    @Select("SELECT distinct OBU_ID FROM AUDIT_PAY_BACK_FEE_FLOW_PAST t WHERE PAY_BACK_FEE_ID = #{paySerialNo}")
    String doFindObo(@Param("paySerialNo") String paySerialNo);

    // 根据obuId修改状态
    @Update("UPDATE AUDIT_PAST_ORDER SET ORDER_STATUS = '1' WHERE OBU_ID = #{obuId}")
    void doUpdateStatus(@Param("obuId") String obuId);

}
