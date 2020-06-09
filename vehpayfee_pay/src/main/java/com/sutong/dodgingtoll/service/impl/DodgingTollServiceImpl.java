/**
 * @Description:
 * @ClassName: DodgingTollServiceImpl
 * @author： Mr.Kong
 * @date: 2019/12/13 16:21
 * @Version： 1.0
 */

package com.sutong.dodgingtoll.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.bjstjh.exception.BaseException;
import com.sutong.bjstjh.result.ConstClass;
import com.sutong.bjstjh.result.ResultCode;
import com.sutong.dodgingtoll.mapper.*;
import com.sutong.dodgingtoll.model.*;
import com.sutong.dodgingtoll.model.vo.AuditPastOrderVo;
import com.sutong.dodgingtoll.model.vo.QueryDodgingListVo;
import com.sutong.dodgingtoll.service.DodgingTollService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;


@Component
@Service
public class DodgingTollServiceImpl implements DodgingTollService {

    private static Logger log = LoggerFactory.getLogger(DodgingTollServiceImpl.class);


    @Autowired
    AuditPayConfirmationMapper auditPayConfirmationMapper;

    @Autowired
    AuditRoadPartResultMapper auditRoadPartResultMapper;

    @Autowired
    AuditRunFeeFlowMapper auditRunFeeFlowMapper;

    @Autowired
    AuditPastOrderMapper auditPastOrderMapper;

    @Autowired
    AuditPastOrderInfoMapper auditPastOrderInfoMapper;
    @Autowired
    AuditEvidenceResultMapper auditEvidenceResultMapper;

    @Autowired
    AuditCodeTableMapper auditCodeTableMapper;

    @Override
    public List<Object> getDodgingTollInfo(String orderId) {


        String encode = null;
      //  String encode1 = fileToBase64("D:\\2.jpg");



        ArrayList<Object> rslist=new ArrayList<>();
        //List<String> roadList = auditRoadPartResultMapper.getRoadListByOid(orderId);
        List<Map<String, Object>> roadList = auditRoadPartResultMapper.getRoadListByOid(orderId);
        if(null==roadList||roadList.isEmpty())
            return null;
        for (int i=0;i<roadList.size();i++){
            Map<String, Object> map = roadList.get(i);
            Object audRoadId = map.get("AUDROADID");
            Object orderId1 = map.get("ORDERID");
            if(i%2==0) {
                encode=fileToBase64("D:\\1.jpeg");
            }else{
                encode=fileToBase64("D:\\2.jpg");
            }

            String codename = getCodename(audRoadId.toString(), "SYS_ROAD_PART_TYPE",null);
            HashMap<String,Object> reMap=new HashMap<>();
            reMap.put("Roda",codename);
            List<AuditEvidenceResult> auditEvidenceList = auditEvidenceResultMapper.getAuditEvidenceListByOid(orderId1.toString());
            if(null!=auditEvidenceList&&!auditEvidenceList.isEmpty()){
                ArrayList<String> list=new ArrayList<>();
               for (int j=0;j<auditEvidenceList.size();j++){
                   String filePath = auditEvidenceList.get(i).getAudFilePath();
                //转base64

                   log.info("encode--------------------->"+encode);
                   //if(j%2==0) {
                       list.add(encode);
                 //  }else{
                     //  list.add(encode1);
                 //  }

               }
                reMap.put("images",list);
                reMap.put("evidence","查看图片");
                rslist.add(reMap);


            }

        }
//        List<AuditMainResult> auditMainResults = auditMainResultMapper.getByorderId(orderId);
//        for (int i = 0; i < auditMainResults.size(); i++) {
//            String audRoadId = auditMainResults.get(i).getAudRoadId();
//            reMap.put("Roda",audRoadId);
//            String evidenceId = auditMainResults.get(i).getAudEvidenceId();
//            List<AuditSubResult> subResults = auditSubResultMapper.getByEvidenceId(evidenceId);
//            for (int j = 0; j < subResults.size(); j++) {
//                String filePath = subResults.get(i).getAudFilePath();
//                //转base64
//                list.add(encode);
//            }
//            reMap.put("images",list);
//            reMap.put("evidence","查看图片");
//            rslist.add(reMap);
//        }

        return rslist;

    }

    @Override
    public List<AuditRunFeeFlow> getDodgingTollList(QueryDodgingListVo vo) throws BaseException{

        String vehicleId = vo.getVehicleId();
        String numColor = vo.getNumColor();
//        if(null!=vehicleId&&null!=numColor){
//            vehicleId=vehicleId+"\u005f"+numColor;
//        }else{
//            throw new BaseException(ResultCode.ERROR, ConstClass.VEHICLEID_IS__NULL);
//        }
//
//        List<String> orderIds = auditMainResultMapper.getOrderIds(vehicleId);
//        if(null==orderIds||orderIds.isEmpty())
//            throw new BaseException(ResultCode.ERROR, ConstClass.DATE_IS_NULL);

        Map<String, Object> map=new HashMap<>();
        map.put("beginDate",vo.getBeginDate());
        map.put("endDate",vo.getEndDate());
        map.put("vehicleId",vehicleId);
        map.put("numColor",numColor);


       // List<AuditMainResultVo> resultVos = auditMainResultMapper.getListInfo(map);
        List<AuditRunFeeFlow> list = auditRunFeeFlowMapper.getRunFeeListByVid(map);

        for (int i=0;i<list.size();i++){
            AuditRunFeeFlow feeFlow = list.get(i);
//            String vehicleId1 = feeFlow.getVehicleId();
//            String vehicleColour = feeFlow.getVehicleColour();
//
//            list.get(i).setVehicleId(vehicleId1+"\u005f"+vehicleColour);
//            String realFee = feeFlow.getRealFee();
//            String orginalFee = feeFlow.getOrginalFee();
//            String oweFee = feeFlow.getOweFee();
//            String owefeee = feeFlow.getOwefeee();
//            list.get(i).setRealFee(changeF2Y(realFee));
//            list.get(i).setOrginalFee(changeF2Y(orginalFee));
//            list.get(i).setOweFee(changeF2Y(oweFee));
//            list.get(i).setOwefeee(changeF2Y(owefeee));

            String cardType = feeFlow.getCardType();
            String vehicleType = feeFlow.getVehicleType();
            String vehicleClass = feeFlow.getVehicleClass();
            feeFlow.setVehicleType(getCodename(vehicleType,"SYS_CAR_TYPE",null));
            feeFlow.setCardType(getCodename(cardType,"SYS_MEDIA_TYPE",null));
            feeFlow.setVehicleClass(getCodename(vehicleClass,"SYS_CAR_CLASS_TYPE",null));

            String audRoadId = feeFlow.getAudRoadId();
            feeFlow.setAudRoadId(getCodename(audRoadId,"SYS_ROAD_PART_TYPE",null));

            String comfirmedEscapeType = feeFlow.getComfirmedEscapeType();
            String tmpCom=null;
            StringBuffer sb=new StringBuffer();
            if(null!=comfirmedEscapeType){

                String[] split1 = comfirmedEscapeType.split("\\|");

                        for (int j = 0; j < split1.length; j++) {
                            String[] split2 = split1[j].split("-");
                            if (split2.length == 1) {
                                feeFlow.setComfirmedEscapeType(getCodename(split2[0], "SYS_ESCAPE_FEE_TYPE",null));
                                break;
                            } else if (split2.length > 1) {

                                tmpCom= getCodename(split2[0], "SYS_ESCAPE_FEE_TYPE",null);
                                sb.append(tmpCom);

                                    tmpCom =  getCodename(split2[1], "SYS_ESCAPE_FEE_TYPE",split2[0]);
                                    sb.append(",");
                                    sb.append(tmpCom);

                                if ((split1.length-1)!=j){
                                    sb.append(";");
                                }
                            } else {
                                feeFlow.setComfirmedEscapeType(null);
                            }
                            feeFlow.setComfirmedEscapeType(sb.toString());
                        }


            }


        }
        return list;
    }

    @Override
    public  String  getEvidenceInfo(String orderId) {


       String encode = fileToBase64("D:\\1.jpeg");
        return encode;


    }

    @Override
    public AuditPastOrderVo getDodgingTollListHistroy(QueryDodgingListVo vo)throws BaseException{
        log.info("histroy-servicce--------------------->");

        String obuId = vo.getObuId();

        int obuNum = auditPastOrderMapper.getCountByObu(obuId);
        if(obuNum>1)
            throw new BaseException(ResultCode.ERROR,ConstClass.DATA_IS_NOT_ONE);
        if(obuNum<=0)
            throw new BaseException(ResultCode.ERROR,ConstClass.DATE_IS_NULL);

        AuditPastOrder orderPast = auditPastOrderMapper.getOrderPastByObId(obuId);
        if(null==orderPast)
            throw new BaseException(ResultCode.ERROR,ConstClass.DATE_IS_NULL);
      //  ArrayList<AuditPastOrderVo> list=new ArrayList<>();
        AuditPastOrderVo auditPastOrderVo=new AuditPastOrderVo();
        BeanUtils.copyProperties(orderPast,auditPastOrderVo);
        List<AuditPastOrderInfo> vos =auditPastOrderInfoMapper.getPaetInfoListByObu(obuId);
        if (null!=vos&&!vos.isEmpty()) {
            for (int i = 0; i < vos.size(); i++) {
                AuditPastOrderInfo auditPastOrderInfo=vos.get(i);
                String doVehicleType = auditPastOrderInfo.getDoVehicleType();
                String orderStatusInfo = auditPastOrderInfo.getOrderStatusInfo();
                String vehicleType = auditPastOrderInfo.getVehicleType();
                String transVehicleType = auditPastOrderInfo.getTransVehicleType();
                vos.get(i).setDoVehicleType(getCodename(doVehicleType,"SYS_CAR_TYPE",null));
                vos.get(i).setVehicleType(getCodename(vehicleType,"SYS_CAR_TYPE",null));
                vos.get(i).setTransVehicleType(getCodename(transVehicleType,"SYS_CAR_TYPE",null));
                vos.get(i).setOrderStatusInfo(getCodename(orderStatusInfo,"SYS_REPAIR_FEE_STATUS",null));

            }
        }
        auditPastOrderVo.setAuditPastOrderInfos(vos);
      //  list.add(auditPastOrderVo);
        return auditPastOrderVo;
    }

    @Override
    public AuditPastOrderInfo getHistroyInfo(String id) {

        AuditPastOrderInfo auditPastOrderInfo = auditPastOrderInfoMapper.getByPrimaryKey(id);

        String doVehicleType = auditPastOrderInfo.getDoVehicleType();
        String orderStatusInfo = auditPastOrderInfo.getOrderStatusInfo();
        String vehicleType = auditPastOrderInfo.getVehicleType();
        String transVehicleType = auditPastOrderInfo.getTransVehicleType();
        auditPastOrderInfo.setDoVehicleType(getCodename(doVehicleType,"SYS_CAR_TYPE",null));
        auditPastOrderInfo.setVehicleType(getCodename(vehicleType,"SYS_CAR_TYPE",null));
        auditPastOrderInfo.setTransVehicleType(getCodename(transVehicleType,"SYS_CAR_TYPE",null));
        auditPastOrderInfo.setOrderStatusInfo(getCodename(orderStatusInfo,"SYS_REPAIR_FEE_STATUS",null));

        return auditPastOrderInfo;


    }
    @Transactional
    @Override
    public void doUpdateVehicleColour(Map<String, Object> map) throws BaseException{
        int num=auditPastOrderInfoMapper.doUpdateColorById(map);
        if(num<=0)
            throw new BaseException(ResultCode.ERROR,ConstClass.COLOURS_UPDATE_ERR);

    }

    @Override
    public AuditPayConfirmation getConfirmationByObu(String obu) throws BaseException{
        int count = auditPayConfirmationMapper.getCountNum(obu);
        if(count>1)
            throw new BaseException(ResultCode.ERROR,ConstClass.DATA_IS_NOT_ONE);
        if(count<=0)
            throw new BaseException(ResultCode.ERROR,ConstClass.DATE_IS_NULL);

        return auditPayConfirmationMapper.getConfirmationByObu(obu);

    }


    public String getCodename(String code,String genName,String parentCode){

        if(null==code||null==genName)
            return null;
        HashMap<String,Object> map=new HashMap<>();
        map.put("code",code);
        map.put("generalName",genName);
        if(null!=parentCode)
            map.put("parentCode",parentCode);
        return auditCodeTableMapper.getNameByCodeAndGen(map);

    }

    public  String changeF2Y(String price) {
        if (null==price)
            return "0";
        return BigDecimal.valueOf(Long.valueOf(price)).divide(new BigDecimal(100)).toString();
    }


    /**
    * @description: 文件转base64
    * @auther: lzq
    * @date: 2019/12/19 16:59
    * @Param path:
    * @return: java.lang.String
    **/
    public String fileToBase64(String path){

        byte[] body = null;
        try {
            body = FileUtils.readFileToByteArray(new File(path));
        } catch (Exception e) {
            throw new BaseException(e.getMessage(), 500, e.getMessage());

        }
        String encode = Base64.getEncoder().encodeToString(body);
        encode="data:image/jpeg;base64,"+encode;
        return encode;
    }
}
