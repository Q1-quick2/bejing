package com.sutong.transflow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.auditCodeTable.mapper.AuditCodeTableMapper;
import com.sutong.transflow.mapper.AuditPastOrderPastMapper;
import com.sutong.transflow.mapper.AuditPayBackFeeFlowMapper;
import com.sutong.transflow.model.AuditPastOrderModel;
import com.sutong.transflow.model.AuditPayBackFeeFlow;
import com.sutong.transflow.service.TransFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Description:
 * @ClassName: TransFlowServiceImpl
 * @author： lzq
 * @date: 2019/12/22 11:43
 * @Version： 1.0
 */
@Component
@Service
public class TransFlowServiceImpl implements TransFlowService{



    @Autowired
    AuditPastOrderPastMapper auditPastOrderPastMapper;

    @Autowired
    AuditPayBackFeeFlowMapper auditPayBackFeeFlowMapper;

    @Autowired
    AuditCodeTableMapper auditCodeTableMapper;

    @Override
    public PageInfo<AuditPayBackFeeFlow> getTransFlowList(String vehicleId, String vehicleColour, Integer pageSize, Integer pageNum) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("vehicleId",vehicleId);
        map.put("vehicleColour",vehicleColour);
        PageHelper.startPage(pageNum, pageSize,true);
        List<AuditPayBackFeeFlow> feeFlows = auditPayBackFeeFlowMapper.getByList(map);
        PageInfo<AuditPayBackFeeFlow> pageInfo = new PageInfo<AuditPayBackFeeFlow>(feeFlows);




        return pageInfo;
    }

    @Override
    public AuditPayBackFeeFlow getTransFlowInfo(String flowId) {

        AuditPayBackFeeFlow auditPayBack = auditPayBackFeeFlowMapper.getById(flowId);
        String flowCreateTime = auditPayBack.getFlowCreateTime();
        auditPayBack.setFlowCreateTime(fromatDate(flowCreateTime));


        return auditPayBack;
    }

    @Override
    public PageInfo<AuditPastOrderModel> getAuditPastOrderResultList(AuditPastOrderModel auditPastOrderModel, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize,true);
        List<AuditPastOrderModel> modelList = auditPastOrderPastMapper.getAuditPastOrderResultList(auditPastOrderModel);
        PageInfo<AuditPastOrderModel> pageInfo = new PageInfo<AuditPastOrderModel>(modelList);
//        HashMap<String,Object> map=new HashMap<>();
//        for (int i = 0; i < modelList.size(); i++) {
//
//            AuditPastOrderModel model = modelList.get(i);
//            String paytype = model.getEndPaytype();
//            if(null!=paytype&&!"".equals(paytype)) {
//                map.put("code", paytype);
//                String name = auditCodeTableMapper.getNameByCodeAndGen(map);
//                modelList.get(i).setEndPaytype(name);
//            }
//
//        }

        return pageInfo;
    }


    @Override
    public AuditPastOrderModel getAuditPastOrderResult(AuditPastOrderModel auditPastOrderModel) {
        AuditPastOrderModel auditPastOrderResult = auditPastOrderPastMapper.getAuditPastOrderResult(auditPastOrderModel);
        String flowCreateTime = auditPastOrderResult.getFlowCreateTime();
        auditPastOrderResult.setFlowCreateTime(fromatDate(flowCreateTime));
        return auditPastOrderResult;
    }


    public  String changeF2Y(Integer price) {
        return BigDecimal.valueOf(Long.valueOf(price)).divide(new BigDecimal(100)).toString();
    }

    public String fromatDate(String strDate){
        if(null==strDate||"".equals(strDate))
            return null;
        if(14!=strDate.length())
            return null;

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMddHHmmss").parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
        return now;
    }
}
