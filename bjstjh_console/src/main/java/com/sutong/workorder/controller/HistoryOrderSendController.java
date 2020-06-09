package com.sutong.workorder.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.bjstjh.result.Result;
import com.sutong.transfer.TransferSendSMS;
import com.sutong.workorder.model.AduitSendsmsQuery;
import com.sutong.workorder.service.IWorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: HistoryOrderSendController
 * @Description: 历史工单发送短信通知接口
 * @author: lichengquan
 * @date: 2019年12月23日 11:42
 * @Version: 1.0
 */
@EnableSwagger2
@CrossOrigin
@RestController
@RequestMapping("/workorder")
@Api(value = "HistoryOrderSendController", tags = "历史工单发送短信通知接口")
public class HistoryOrderSendController {

    private static final Logger log = LoggerFactory.getLogger(HistoryOrderSendController.class);

    @Autowired
    private TransferSendSMS transferSendSMS;

    @Reference
    private IWorkOrderService iWorkOrderService;


    @ApiOperation("手动发送短信")
    @PostMapping("/send/sms")
    public Result sendSms(@RequestBody List<AduitSendsmsQuery> aduitSendsmsQueries) {
        log.info("入参：{}", aduitSendsmsQueries);
        if (CollectionUtils.isEmpty(aduitSendsmsQueries)) {
            return Result.error().message("通知短信电话不能为空");
        }
        String message = "测试通知短信";
        List<String> successKeyIds = new ArrayList<>();
        for (AduitSendsmsQuery aduitSendsmsQuery : aduitSendsmsQueries) {
//            Map<String, Object> resultMap = transferSendSMS.sendSMS(aduitSendsmsQuery.getContactTelePhoneNo(), message);
            Map resultMap = new HashMap<String, Object>();
            resultMap.put("success", true);
            if (resultMap.containsValue(true)) {
                successKeyIds.add(aduitSendsmsQuery.getSerialId());
            } else {
                return Result.error().message("发送短信失败");
            }
        }
        Integer integer = iWorkOrderService.doUpdateHistoryOrder(successKeyIds);
        if (integer == successKeyIds.size()) {
            return Result.ok();
        }
        return Result.error().message("更新状态失败");
    }


}
