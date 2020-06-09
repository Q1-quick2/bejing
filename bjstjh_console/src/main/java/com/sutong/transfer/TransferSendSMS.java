package com.sutong.transfer;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 发送短信类
 * @auther: pengjien
 * @date: 2019/12/14 10:08
 **/
@Component
public class TransferSendSMS {
    Logger log = LoggerFactory.getLogger(TransferSendSMS.class);
    @Value("${sms.ip}")
    private String smsIp;
    @Value("${sms.mothed}")
    private String smsMothed;
    @Value("${sms.SOAPActionURI}")
    private String SOAPActionURI;
    @Value("${sms.Namespace}")
    private String Namespace;
    @Value("${sms.ChannelID}")
    private String ChannelID;
    /**
     * @description: 发送短信方法
     * @auther: pengjien
     * @date: 2019/12/14 10:03
     * @Param mobilNo：手机号
     * @Param content: 短信内容
     * @return: java.util.Map<java.lang.String,java.lang.String>
     **/
    public Map<String,Object> sendSMS(String mobilNo, String content) {
        Map resultMap = new HashMap<String,Object>();
        try {
            String endpoint = smsIp+"sms_service.asmx?wsdl";
            Service service=new Service();
            Call call=(Call)service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(endpoint));
            call.setOperationName(new QName(Namespace,smsMothed));
            //必须这样增加参数
            call.addParameter(new QName(Namespace,"mobilNO"), XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter(new QName(Namespace,"content"),XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter(new QName(Namespace,"sendTime"),XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter(new QName(Namespace,"TimeStamp"),XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter(new QName(Namespace,"interval"),XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter(new QName(Namespace,"SendType"),XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter(new QName(Namespace,"IfCallBack"),XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter(new QName(Namespace,"CallBackurl"),XMLType.XSD_STRING,ParameterMode.IN);
            call.addParameter(new QName(Namespace,"ChannelID"),XMLType.XSD_STRING,ParameterMode.IN);
            call.setReturnType(XMLType.XSD_STRING);
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(SOAPActionURI);
            call.setEncodingStyle("utf-8");
            String result=(String)call.invoke(new Object[]{mobilNo,content,null,null,null,null,null,null,ChannelID});
            if(result.contains("发送成功")){
                resultMap.put("success",true);
                resultMap.put("msg",result);
            }else{
                resultMap.put("success",false);
                resultMap.put("msg",result);
        }
            log.info("短信接口调用成功，返回信息："+result+",发送手机号是："+mobilNo);
        } catch (Exception e) {
            resultMap.put("success",false);
            resultMap.put("msg","发送失败，系统错误");
            log.error("短信接口调用异常："+e.getMessage());
            return resultMap;
        }
        return resultMap;
    }
    /**
     * @description: 占位符替换内容，返回完整信息
     * @auther: pengjien
     * @date: 2019/12/14 14:48
     * @Param content:模板
     * @Param strs:替换值
     * @return: java.lang.String
     **/
    public String replaceContent(String content,String[] strs){
        String format = MessageFormat.format(content, strs);
        return format;
    }

}
