package com.sutong.transflow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sutong.bjstjh.result.ConstClass;
import com.sutong.bjstjh.result.Result;
import com.sutong.transflow.model.AuditPastOrderModel;
import com.sutong.transflow.model.AuditPayBackFeeFlow;
import com.sutong.transflow.service.TransFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Description:补费流水相关查询
 * @ClassName: TransFlowController
 * @author： 李振全
 * @date: 2019/12/22 11:14
 * @Version： 1.0
 */

@Api(tags={"补费流水相关查询"})
@CrossOrigin
@RestController
@RequestMapping("/transflow")
public class TransFlowController {
    private static Logger log = LoggerFactory.getLogger(TransFlowController.class);

    @Reference(timeout=50000)
    private TransFlowService transFlowService;



    /**
     * 历史补费流水列表
     * @return
     */
    @PostMapping(value = "/getTransFlowHistroyList")
    public Result getAuditpastorderController(
            @RequestParam(value = "obuId", required = false) String obuId,
            @RequestParam(value = "begintime", required = false) String begintime,
            @RequestParam(value = "endtime", required = false) String endtime,
            @RequestParam(value = "pageNum", required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize){


        log.info("obuId----------------->>>"+obuId);
        log.info("begintime----------------->>>"+begintime);

        AuditPastOrderModel model = new AuditPastOrderModel();
        model.setObuId(obuId);
        model.setBegintime(begintime);
        model.setEndtime(endtime);

        PageInfo<AuditPastOrderModel> resultList = transFlowService.getAuditPastOrderResultList(model, pageNum, pageSize);

        return Result.ok().data("resultdata",resultList);
    }

    /**
     * 历史补费详情
     * @return
     */
    @PostMapping(value = "/getTransFlowHistroyInfo")
    public Result getAuditpastorderInfoController(@RequestParam(value = "id", required = true) String id){
        AuditPastOrderModel model = new AuditPastOrderModel();
        model.setId(id);
        AuditPastOrderModel  lists = transFlowService.getAuditPastOrderResult(model);
        return Result.ok().data("resultdata",lists);
    }


    /**
    * @description: 获取流水列表
    * @auther: lzq
    * @date: 2019/12/22 11:34
    * @Param vehicleId: 车牌号
     * @Param vehicleColour: 车牌颜色
     * @Param pageNum: 当前页
     * @Param pageSize:  每页记录数
    * @return: com.sutong.bjstjh.result.Result
    **/
    @ApiOperation("获取流水列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "vehicleId", value = "车牌号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "vehicleColour", value = "车牌颜色", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页记录数", required = true, dataType = "Integer")
    })
    @PostMapping("getTransFlowList")
    public Result getTransFlowList(@RequestParam(value = "vehicleId", required = false) String vehicleId,
                                   @RequestParam(value = "vehicleColour", required = false) String vehicleColour,
                                   @RequestParam(value = "pageNum", required = true, defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize){

        log.info("vehicleId----------------->>>"+vehicleId);
        log.info("vehicleColour----------------->>>"+vehicleColour);
        PageInfo<AuditPayBackFeeFlow> info = transFlowService.getTransFlowList(vehicleId, vehicleColour, pageSize, pageNum);
        HashMap<String,Object> map=new HashMap<>();
        Result ok = Result.ok();
        if (info.getList().isEmpty()||null==info.getList())
            ok.message(ConstClass.DATE_IS_NULL);
        map.put("infoList",info);
        ok.setData(map);
        return ok;




    }

    @PostMapping("getTransFlowInfo")
    public Result getTransFlowInfo(@RequestParam(value = "flowId", required = true) String flowId){
        AuditPayBackFeeFlow flowInfo = transFlowService.getTransFlowInfo(flowId);
        HashMap<String,Object> map=new HashMap<>();
        Result ok = Result.ok();
        map.put("infoList",flowInfo);
        ok.setData(map);
        return ok;
    }

}
