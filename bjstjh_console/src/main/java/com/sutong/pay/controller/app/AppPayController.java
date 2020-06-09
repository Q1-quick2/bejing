/**
 * @Description: APP支付Controller
 * @ClassName: AppPayController
 * @author： WangLei
 * @date: 2019/12/14 10:07
 * @Version： 1.0
 */
package com.sutong.pay.controller.app;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.bjstjh.entity.PayEndInformModel;
import com.sutong.bjstjh.entity.pay.AppPayParameterVo;
import com.sutong.bjstjh.entity.pay.AppPayRequestVo;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.NotNullUtil;
import com.sutong.pay.service.AppPayResultService;
import com.sutong.pay.service.AppPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 王磊 on 2019/12/14.
 */
@RestController
@CrossOrigin
public class AppPayController {

    Logger log = LoggerFactory.getLogger(AppPayController.class);

    @Reference
    private AppPayService appPayService;

    /**
     * @description: 非历史逃费支付预下单
     * @auther: WangLei
     * @date: 2019/12/25 18:50
     * @Param requestVo: 参数对象
     * @return: com.sutong.bjstjh.result.Result
     **/
    @PostMapping(value = "/appPayPreOrder",produces = "application/json;charset=UTF-8")
    public synchronized Result appPayPreOrder(@RequestBody AppPayRequestVo requestVo,HttpServletRequest request){
        try {
            log.info("进入非历史逃费支付预下单Controller");
            //将请求参数对象存到session中，用来记录日志
            request.getSession().setAttribute("requestVo",requestVo);
            //判断补费状态
            if (!NotNullUtil.isEmpty(requestVo) && "1".equals(requestVo.getOrderType())) { //非历史
                String[] orderIds = requestVo.getOrderIds();
                for (String orderId : orderIds) {
                    String orderStatus = appPayService.doFindOrderStatus(orderId);
                    if ("1".equals(orderStatus)) {
                        return Result.alreadyFee().data("message", "存在已补费的工单，请重新选择！");
                    }
                }
            } else {
                log.error("非历史逃费判断补费状态出错！");
                return Result.error();
            }
            //调用“支付平台”APP支付预下单接口
            AppPayParameterVo parameterVo = appPayService.doCallAppPayPreOrder(requestVo);
            if (!NotNullUtil.isEmpty(parameterVo)) {
                log.info("给H5返回的结果为：" + parameterVo);
                return Result.ok().data("parameterVo", parameterVo);
            } else {
                log.error("非历史逃费调用APP支付预下单接口返回的数据为空！");
                return Result.error();
            }
        } catch (Exception e) {
            log.error("非历史逃费支付预下单出错！");
            log.error("异常信息：" + e);
            return Result.error();
        }
    }

    /**
     * @description: 历史逃费支付预下单
     * @auther: WangLei
     * @date: 2019/12/28 11:26
     * @Param requestVo: 参数对象
     * @return: com.sutong.bjstjh.result.Result
     **/
    @PostMapping(value = "/appPayPreOrderPast",produces = "application/json;charset=UTF-8")
    public synchronized Result appPayPreOrderPast(@RequestBody AppPayRequestVo requestVo, HttpServletRequest request){
        try {
            log.info("进入历史逃费支付预下单Controller");
            //将请求参数对象存到session中，用来记录日志
            request.getSession().setAttribute("requestVo",requestVo);
            //判断补费状态
            if (!NotNullUtil.isEmpty(requestVo) && "2".equals(requestVo.getOrderType())) { //历史
                String orderStatus = appPayService.doFindOrderStatusPast(requestVo.getObuId());
                if ("1".equals(orderStatus)) {
                    return Result.alreadyFee().data("message", "该条逃费记录已补费，请重新选择！");
                }
            } else {
                log.error("历史逃费判断补费状态出错！");
                return Result.error();
            }
            //调用“支付平台”APP支付预下单接口
            AppPayParameterVo parameterVo = appPayService.doCallAppPayPreOrder(requestVo);
            if (!NotNullUtil.isEmpty(parameterVo)) {
                log.info("给H5返回的结果为：" + parameterVo);
                return Result.ok().data("parameterVo", parameterVo);
            } else {
                log.error("历史逃费调用APP支付预下单接口返回的数据为空！");
                return Result.error();
            }
        } catch (Exception e) {
            log.error("历史逃费支付预下单出错！");
            log.error("异常信息：" + e);
            return Result.error();
        }
    }



    @Reference
    private AppPayResultService appPayResultService;

    @RequestMapping("/test")
    public Result test(String orderNo) throws Exception {
        PayEndInformModel vo = appPayResultService.doCallAppPayResult(orderNo);
        return Result.ok().data("vo",vo);
    }



}