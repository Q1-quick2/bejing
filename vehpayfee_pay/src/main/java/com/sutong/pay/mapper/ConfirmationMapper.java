package com.sutong.pay.mapper;

import com.sutong.bjstjh.entity.AuditPastOrderEntity;
import com.sutong.bjstjh.entity.ConfirmationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by main on 2019/12/18.
 */
@Mapper
@Repository
public interface ConfirmationMapper {

    // 高速公路通行费补费确认单    保存
    Boolean doUpdateConfirmationInfo(ConfirmationEntity confirmationEntity);

    // 获取确认单信息
    @Select(" SELECT DISTINCT CUSTOMER_NAME as customerName,OWE_FEE AS oweFee,TRANS_NUM AS transNum FROM AUDIT_PAST_ORDER WHERE OBU_ID =  #{obu}")
    AuditPastOrderEntity doFindListQuery(@Param("obu") String obu);

    //  获取确认单信息    车牌号   车型
    @Select("SELECT DISTINCT ENSTATION AS raid1,EXSTATION AS raid2,DO_VEHICLE_TYPE AS doVehicleType,DO_VEHICLE_ID AS doVehicleId, TRANS_CARD_ID AS payCardNumber FROM AUDIT_PAST_ORDER_INFO WHERE TRANS_OBU_ID = #{obu}")
    List<ConfirmationEntity> doFindVehicle(@Param("obu") String obu);

    // 查询是否存在
    ConfirmationEntity doConfirmationInfo(String obu);

    // 高速公路通行费补费确认单   修改
    Boolean doUpdateConfirmat(ConfirmationEntity confirmation);

    //获取开始结束时间
    @Select("SELECT  MAX(TRANS_TIME) AS payEndTime,MIN(TRANS_TIME) AS payTime FROM AUDIT_PAST_ORDER_INFO WHERE TRANS_OBU_ID = #{obu}")
    ConfirmationEntity doFindTime(@Param("obu") String obu);

    @Select("SELECT DISTINCT tt.ROAD_NAME FROM AUDIT_GANTRY_MOUNTING t LEFT JOIN AUDIT_ROAD_SECTION tt ON t.ROAD_ID =tt.ROAD_ID WHERE t.GANTRY_NAME LIKE '%${raid1}%'")
    String doFindRaid1(@Param("raid1") String raid1);

    @Select("SELECT DISTINCT tt.ROAD_NAME FROM AUDIT_GANTRY_MOUNTING t LEFT JOIN AUDIT_ROAD_SECTION tt ON t.ROAD_ID =tt.ROAD_ID WHERE t.GANTRY_NAME LIKE '%${raid2}%'")
    String doFindRaid2(@Param("raid2") String raid2);

    // 获取确认单  ID
    @Select("SELECT DISTINCT EXTERNAL_ORDERNO AS payId ,EVENT_TYPECODE AS payInfo FROM AUDIT_WORKORDER_HISTORY_TABLE WHERE OBUNO = #{obu}")
    List<ConfirmationEntity> getConfirId(@Param("obu") String obu);
}
