package com.sutong.pay.controller.app.confiletool;

import com.sutong.bjstjh.result.Result;
import com.sutong.workorder.controller.WorkOrderController;
import com.sutong.workorder.entity.AuditLogManagementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:调用发票接口后将数据插入接口日志表工具类
 * @ClassName: InvoiceLogUtil
 * @author： liyan
 * @date: 2020/1/9 17:19
 * @Version： 1.0
 */
@Component
public class InvoiceLogUtil {

    @Autowired
    private WorkOrderController workOrderController;

    /**
     * @description: 插入接口日志表工具类--调用发票接口后将数据
     * @auther: liyan
     * @date: 2020/1/9 17:41
     * @param: [type 接口名称, requestParam传入参数, returnParam返回参数]
     * @return: com.sutong.bjstjh.result.Result
     **/
    public Result insertLog (String type, String requestParam, String returnParam){
        AuditLogManagementEntity auditLogManagement = new AuditLogManagementEntity();
//        auditLogManagement.setLogId();
        auditLogManagement.setSystemName("发票系统");
        auditLogManagement.setSystemCode("1");
        if(type.equals("173")) {
            auditLogManagement.setInterfaceName("稽查补缴交易同步接口（B20100173）");
        }
        if(type.equals("174")) {
            auditLogManagement.setInterfaceName("根据交易编号查询交易信息（B20100174）");
        }
        if(type.equals("23")) {
            auditLogManagement.setInterfaceName("开票申请接口（B20100023）");
        }
        if(type.equals("55")) {
            auditLogManagement.setInterfaceName("根据交易查询发票信息（B20100055）");
        }
        if(type.equals("65")) {
            auditLogManagement.setInterfaceName("发票换开手动调用接口（B20100065）");
        }
        auditLogManagement.setRequestParameterInfo(requestParam);
        auditLogManagement.setReturnParameterInfo(returnParam);
//        auditLogManagement.setRmFlag();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        auditLogManagement.setCreateTime(df.format(new Date()));//获取当前系统时间
//        auditLogManagement.setUpdateTime();

        Result result=workOrderController.doInsertLogManagement(auditLogManagement);
        return result;
    }

}