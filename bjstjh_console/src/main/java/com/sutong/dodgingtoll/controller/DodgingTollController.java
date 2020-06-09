

package com.sutong.dodgingtoll.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.sutong.bjstjh.exception.BaseException;
import com.sutong.bjstjh.result.ConstClass;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.dodgingtoll.model.AuditPastOrderInfo;
import com.sutong.dodgingtoll.model.AuditPayConfirmation;
import com.sutong.dodgingtoll.model.AuditRunFeeFlow;
import com.sutong.dodgingtoll.model.vo.AuditPastOrderVo;
import com.sutong.dodgingtoll.model.vo.QueryDodgingListVo;
import com.sutong.dodgingtoll.service.DodgingTollService;
import com.sutong.transfer.TransferIssue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @ClassName: DodgingTollController
 * @author： lzq
 * @date: 2019/12/13 16:24
 * @Version： 1.0
 */

@Api(tags={"非历史逃费与历史逃费查询接口"})
@CrossOrigin
@RestController
@RequestMapping(value = "/dodgingtoll")
public class DodgingTollController {
    private static Logger log = LoggerFactory.getLogger(DodgingTollController.class);

    @Reference(timeout=50000)
    private DodgingTollService dodgingTollService;

    @Autowired
    TransferIssue transferIssue;


    @ApiOperation("获取非历史逃费详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", required = true, dataType = "String")
    })
    @PostMapping(value = "/getDodgingTollInfo")
    public Result getDodgingTollInfo(@RequestParam(value="orderId",required =true) String orderId){



        List<Object> results = dodgingTollService.getDodgingTollInfo(orderId);


        if (null==results||results.isEmpty())
            return Result.error().message(ConstClass.DATE_IS_NULL);
        HashMap<String,Object> map=new HashMap<>();
        map.put("infoList",results);
        return Result.ok().data(map);

    }

    @ApiOperation("获取非历史逃费列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "vo", value = "查询条件", required = true, dataType = "QueryDodgingListVo")
    })
    @PostMapping(value = "/getDodgingTollList")
    public Result getDodgingTollList(@RequestBody QueryDodgingListVo vo){

        if(null==vo)
            return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.PARAM_EXCEPTION);
        String vehicleId = vo.getVehicleId();
        String numColor = vo.getNumColor();
        String certificateType = vo.getCertificateType();
        String userCard = vo.getUserCard();
        if(null==certificateType||null==userCard)
            return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.PARAM_EXCEPTION);
        Map<String, Object> objectMap = transferIssue.transferIssueLogin();
        Boolean sessionKeySucc = (Boolean)objectMap.get("success");
        if(!sessionKeySucc) {
            return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.SESSION_KEY_NULL);
        }

        Map<String, Object> etcInfo = transferIssue.transferQueryETCInfo(vehicleId,numColor, null, null, objectMap.get("data").toString());
        log.info("transferIssue---------------->"+etcInfo);
        Boolean success = (Boolean)etcInfo.get("success");
        String msg = (String) etcInfo.get("msg");
        if(success){
            JSONObject data = (JSONObject)etcInfo.get("data");
            JSONObject userInfo = data.getJSONObject("UserInfo");
            String CertificateTypeCode = userInfo.getString("CertificateTypeCode");
            String certificateNo = userInfo.getString("CertificateNo");
            log.info("参数值vehicleId---------------->"+vehicleId);
            log.info("参数值userCard---------------->"+userCard);
            log.info("参数值certificateType---------------->"+certificateType);
            log.info("返回值CertificateTypeCode---------------->"+CertificateTypeCode);
            log.info("返回值certificateNo---------------->"+certificateNo);
            if(certificateType.equals(ConstClass.CARD_TYPE_OTHER)){
                if(ConstClass.CARD_TYPE_OTHER_ENTER.equals(CertificateTypeCode)){
                    if(!userCard.equals(certificateNo)){
                        return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.USER_INFO_NOT_EXISTENT);
                    }
                }else if (ConstClass.CARD_TYPE_OTHER_PER.equals(CertificateTypeCode)){
                    if(!userCard.equals(certificateNo)){
                        return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.USER_INFO_NOT_EXISTENT);
                    }
                }else{
                    return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.USER_INFO_NOT_EXISTENT);
                }

            }else if(!certificateType.equals(CertificateTypeCode)||!userCard.equals(certificateNo)){

                return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.USER_INFO_NOT_EXISTENT);
            }


        }else{

            return Result.error().code(ResultCode.USER_INF_ERROR).message(msg);
        }



//        List<AuditMainResultVo> dodgingTollList = null;
//        try {
//            dodgingTollList = dodgingTollService.getDodgingTollList(vo);
//
//        } catch (BaseException e) {
//            return Result.error(e.getCode(),e.getMsg());
//        }
        List<AuditRunFeeFlow> dodgingTollList = dodgingTollService.getDodgingTollList(vo);


        if (null==dodgingTollList||dodgingTollList.isEmpty())
            return  Result.error().message(ConstClass.DATE_IS_NULL);
        HashMap<String, Object> map = new HashMap<>();
        map.put("infoList", dodgingTollList);
        return Result.ok().data(map);



    }

    @PostMapping(value = "/getEvidenceInfo")
    //public ResponseEntity<byte[]> getEvidenceInfo(String path){
    public Result getEvidenceInfo(@RequestParam(value="orderId",required =true) String orderId){

        String evidenceInfo = dodgingTollService.getEvidenceInfo(orderId);
//        HttpHeaders headers=new HttpHeaders();//设置响应头
//		        headers.add("Content-Disposition", "attachment;fileName=\"" + 213 +"\"");
//        ResponseEntity<byte[]> responseEntity = ResponseEntity
//		                .ok()
//		                .headers(headers)
//		                .contentLength(body.length)
//		                .contentType(MediaType.parseMediaType("video/x-msvideo"))
////		                .body(body);
//        String evidenceInfo = dodgingTollService.getEvidenceInfo(orderId);
        HashMap<String,Object> map=new HashMap<>();

        map.put("fileDate",evidenceInfo);
        Result ok = Result.ok();
        ok.setData(map);
        return ok;


    }

    @ApiOperation("获取历史逃费列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "vo", value = "查询条件", required = true, dataType = "QueryDodgingListVo")
    })
    @PostMapping(value = "/getHistroyList")
    public Result getHistroyList(@RequestBody QueryDodgingListVo vo){

        if(null==vo)
            return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.PARAM_EXCEPTION);
        String certificateType = vo.getCertificateType();
        String userCard = vo.getUserCard();
        String obuId = vo.getObuId();
        String VehPlateColorCode=null;
        if(null==certificateType||null==userCard)
            return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.PARAM_EXCEPTION);
        Map<String, Object> objectMap = transferIssue.transferIssueLogin();
        Boolean sessionKeySucc = (Boolean)objectMap.get("success");
        if(!sessionKeySucc) {
            return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.SESSION_KEY_NULL);
        }
        Map<String, Object> etcInfo = transferIssue.transferQueryETCInfo(null, null, null, obuId, objectMap.get("data").toString());
        log.info("transferIssue---------------->"+etcInfo);
        Boolean success = (Boolean)etcInfo.get("success");
        String msg = (String) etcInfo.get("msg");
        if(success){
            JSONObject data = (JSONObject)etcInfo.get("data");
            VehPlateColorCode =data.getString("VehPlateColorCode");
            JSONObject userInfo = data.getJSONObject("UserInfo");
            String CertificateTypeCode = userInfo.getString("CertificateTypeCode");


            String certificateNo = userInfo.getString("CertificateNo");
            log.info("参数值userCard---------------->"+userCard);
            log.info("参数值certificateType---------------->"+certificateType);
            log.info("返回值CertificateTypeCode---------------->"+CertificateTypeCode);
            log.info("返回值certificateNo---------------->"+certificateNo);
            if(certificateType.equals(ConstClass.CARD_TYPE_OTHER)){
                if(ConstClass.CARD_TYPE_OTHER_ENTER.equals(CertificateTypeCode)){
                    if(!userCard.equals(certificateNo)){
                        return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.USER_INFO_NOT_EXISTENT);
                    }
                }else if (ConstClass.CARD_TYPE_OTHER_PER.equals(CertificateTypeCode)){
                    if(!userCard.equals(certificateNo)){
                        return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.USER_INFO_NOT_EXISTENT);
                    }
                }else{
                    return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.USER_INFO_NOT_EXISTENT);
                }

            }else if(!certificateType.equals(CertificateTypeCode)||!userCard.equals(certificateNo)){

                return Result.error().code(ResultCode.USER_INF_ERROR).message(ConstClass.USER_INFO_NOT_EXISTENT);
            }


        }else{

            return Result.error().code(ResultCode.USER_INF_ERROR).message(msg);
        }



            AuditPastOrderVo histroy=null;

        try{
            if(null==VehPlateColorCode)
                return Result.error().message(ConstClass.COLOURS_UPDATE_ERR);

            histroy = dodgingTollService.getDodgingTollListHistroy(vo);
            List<AuditPastOrderInfo> auditPastOrderInfos = histroy.getAuditPastOrderInfos();
            if(null==auditPastOrderInfos||auditPastOrderInfos.isEmpty())
                return  Result.error().message(ConstClass.DATE_INFO_IS_NULL);
            ArrayList<String> list=new ArrayList<>();
            for (int i = 0; i < auditPastOrderInfos.size(); i++) {
                AuditPastOrderInfo info = auditPastOrderInfos.get(i);
                String id=info.getId();
                if(null==info.getVehicleColour()||"".equals(info.getVehicleColour()))
                  list.add(id);
            }
            if(list.size()>0){
                HashMap<String,Object> map=new HashMap<>();
                map.put("vehicleColour",VehPlateColorCode);
                map.put("list",list);
                dodgingTollService.doUpdateVehicleColour(map);

            }


        } catch (BaseException e) {
            return Result.error(e.getCode(),e.getMsg());
        }


        HashMap<String, Object> map = new HashMap<>();
        ArrayList<AuditPastOrderVo> listInfo=new ArrayList<>();
        listInfo.add(histroy);
        map.put("infoList", listInfo);
        return Result.ok().data(map);

    }

    @ApiOperation("获取历史逃费详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id号", required = true, dataType = "String")
    })
    @PostMapping(value = "/getHistroyListInfo")
    public Result getHistroyInfo(@RequestParam(name = "id",required = true) String id){

        AuditPastOrderInfo info = dodgingTollService.getHistroyInfo(id);


        if (null==info)
            return Result.error().message(ConstClass.DATE_IS_NULL);
        HashMap<String, Object> map = new HashMap<>();
        map.put("infoList", info);
        return Result.ok().data(map);

    }


    @ApiOperation("获取确认单信息，查看")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "obuId", value = "obu号", required = true, dataType = "String")
    })
    @PostMapping(value = "/getConfirmation")
    public Result getConfirmation(@RequestParam(name = "obuId",required = true) String obuId){
        if(null==obuId||"".equals(obuId))
            return Result.error().message(ConstClass.PARAM_IS_NULL);
        try {
            AuditPayConfirmation confirmation = dodgingTollService.getConfirmationByObu(obuId);
            if (null==confirmation)
                return Result.error().message(ConstClass.DATE_IS_NULL);
            HashMap<String, Object> map = new HashMap<>();
            map.put("infoList", confirmation);
            return Result.ok().data(map);

        }catch (BaseException e){
            return Result.error(e.getCode(),e.getMsg());
        }

    }




}
