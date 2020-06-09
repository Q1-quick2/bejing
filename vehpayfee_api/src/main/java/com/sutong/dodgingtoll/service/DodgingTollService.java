package com.sutong.dodgingtoll.service;

import com.sutong.bjstjh.exception.BaseException;
import com.sutong.dodgingtoll.model.AuditPastOrderInfo;
import com.sutong.dodgingtoll.model.AuditPayConfirmation;
import com.sutong.dodgingtoll.model.AuditRunFeeFlow;
import com.sutong.dodgingtoll.model.vo.AuditPastOrderVo;
import com.sutong.dodgingtoll.model.vo.QueryDodgingListVo;

import java.util.List;
import java.util.Map;

/**
* @description: 逃费查询相关
* @auther: 李振全
* @date: 2019/12/14 20:50
*
**/
public interface DodgingTollService {
   /**
   * @description: 根据订单id获取路方信息
   * @auther:lzq
   * @date: 2019/12/14 20:51
   * @Param orderId:
   * @return:
   **/
    public List<Object> getDodgingTollInfo(String orderId);

   /**
   * @description: 根据车牌，颜色，用户名等信息获取逃费非历史记录
   * @auther:lzq
   * @date: 2019/12/14 20:53
   * @Param map:
   * @return: java.util.List<com.sutong.dodgingtoll.model.AuditRunFeeFlow>
   **/
    public List<AuditRunFeeFlow> getDodgingTollList(QueryDodgingListVo vo);

    /**
    * @description:根据订单id获取证据文件，base64格式字符串
    * @auther: lzq
    * @date: 2019/12/17 15:01
    * @Param orderId:
    * @return: java.lang.String
    **/
    public String  getEvidenceInfo(String orderId);

   /**
   * @description: 查询历史逃费信息
   * @auther: lzq
   * @date: 2019/12/17 15:17
   * @Param vo:
   * @return:
   **/
    public AuditPastOrderVo getDodgingTollListHistroy(QueryDodgingListVo vo)throws BaseException;

    /**
    * @description:查询历史逃费详情信息
    * @auther: lzq
    * @date: 2019/12/18 19:30
    * @Param id:
    * @return: com.sutong.dodgingtoll.model.AuditPastOrderInfo
    **/
    public AuditPastOrderInfo getHistroyInfo(String id);

    /**
    * @description:根据id更新车牌颜色
    * @auther: lzq
    * @date: 2020/1/3 15:44
    * @Param map:
    * @return: int
    **/
    public void  doUpdateVehicleColour(Map<String,Object> map)throws BaseException;

    /**
    * @description: 根据obu获取确认单
    * @auther: lzq
    * @date: 2020/1/5 14:38
    * @Param map:
    * @return: com.sutong.bjstjh.entity.ConfirmationEntity
    **/
    public AuditPayConfirmation getConfirmationByObu(String obu)throws BaseException;



}
