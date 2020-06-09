package com.sutong.transfer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sutong.common.model.ConfigModel;
import com.sutong.common.service.ConfigService;
import com.sutong.workorder.model.CardLiftingDTO;
import com.sutong.workorder.model.CardLiftingQuery;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TransferIssue
 * @author： pengjien
 * @date: 2019/12/14 19:38
 * @Description: 调用发行方接口类
 */
@Component
public class TransferIssue {
    Logger log = LoggerFactory.getLogger(TransferIssue.class);
    @Value("${issue.clientIp}")
    private String clientIp;
    @Value("${issue.url}")
    private String url;
    @Value("${issue.operatorNo}")
    private String operatorNo;
    @Value("${issue.operatorPassWord}")
    private String operatorPassWord;

    @Reference
    private ConfigService configService;

    @Autowired
    private RestTemplate restTemplate;
    /**
     * @description:查询发行用户信息接口
     * @auther: pengjien
     * @date: 2019/12/15 11:42
     * @Param VehPlateNo: 车牌号
     * @Param VehPlateColorCode: 车牌颜色
     * @Param CardNo:ETC卡号
     * @Param ObuNo:OBU卡号
     * @return: java.lang.Object
     **/
    public Map<String, Object> transferQueryETCInfo(String VehPlateNo, String VehPlateColorCode, String CardNo, String ObuNo,String sessionKey) {
        Map<String, Object> resultMap = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject requestJson = new JSONObject();
        if(null != VehPlateNo && !"".equals(VehPlateNo)){
            requestJson.put("VehPlateNo",VehPlateNo);
        }
        if(null != VehPlateColorCode && !"".equals(VehPlateColorCode)){
            requestJson.put("VehPlateColorCode",VehPlateColorCode);
        }
        if(null != CardNo && !"".equals(CardNo)){
            requestJson.put("CardNo",CardNo);
        }
        if(null != ObuNo && !"".equals(ObuNo)){
            requestJson.put("ObuNo",ObuNo);
        }
//      requestJson.put("ClientIP","172.20.53.103");
//      requestJson.put("SessionKey","0b9486484d6c9aaf2f737a6cfc4e9be2");
        //每次请求必带值
        requestJson.put("ClientIP",clientIp);
        requestJson.put("SessionKey",sessionKey);
        String requestURL = url+"est/est03234.xhtml";
        HttpPost httpPost = new HttpPost(requestURL);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(180 * 1000)
                .setConnectTimeout(180 * 1000).setSocketTimeout(180 *1000).setRedirectsEnabled(false).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type","aplication/json");
        httpPost.setEntity(new StringEntity(requestJson.toJSONString(), ContentType.create("application/json","GBK")));
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                JSONObject resultJson = JSONObject.parseObject(result);
                //respCode等于0
                if(0 == Integer.parseInt(resultJson.get("RespCode")+"")){
                    resultMap.put("success",true);
                    resultMap.put("msg","调用接口成功");
                    resultMap.put("data",resultJson);
                }else{
                    resultMap.put("success",false);
                    resultMap.put("msg",resultJson.get("RespMsg")+"");
                }
            }else{
                resultMap.put("success",false);
                resultMap.put("msg","调用接口失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("success",false);
            resultMap.put("msg","调用接口异常");
            return  resultMap;
        }
        return resultMap;
    }
    /**
     * @description: 获取发行登陆sessionKey
     * @auther: pengjien
     * @date: 2019/12/21 10:43
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String,Object> transferIssueLogin(){
        Map<String,Object> resultMap = new HashMap<>();
        //调用获取发行sessionKey接口，先去数据库中查找，如果存在则用数据库中数据，如果数据库中不存在则调用发行登陆接口获取sessionKey
        ConfigModel queryConfigModel = new ConfigModel();
        queryConfigModel.setType(1);
        List<ConfigModel> configModelList = configService.doFindConfigList(queryConfigModel);
        if(configModelList.size() > 0 ){
            String sessionKey = configModelList.get(0).getConfigValue();
            resultMap.put("success",true);
            resultMap.put("msg","调用接口成功");
            resultMap.put("data",sessionKey);
            return resultMap;
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject requestJson = new JSONObject();
        requestJson.put("OperatorNo",operatorNo);
        requestJson.put("OperatorPassWord",operatorPassWord);
        requestJson.put("ClientIP",clientIp);
        String requestURL = url+"/est/est08069.xhtml";
        HttpPost httpPost = new HttpPost(requestURL);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(180 * 1000)
                .setConnectTimeout(180 * 1000).setSocketTimeout(180 *1000).setRedirectsEnabled(false).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type","aplication/json");
        httpPost.setEntity(new StringEntity(requestJson.toJSONString(), ContentType.create("application/json","GBK")));
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                JSONObject resultJson = JSONObject.parseObject(result);
                //respCode等于0
                if(0 == Integer.parseInt(resultJson.get("RespCode")+"")){
                    JSONArray jsonArray = JSONArray.parseArray(resultJson.get("ResultStr").toString());
                    String jsonObject = JSONObject.parseObject(jsonArray.get(jsonArray.size() - 1).toString()).get("sessionKey").toString();
                    resultMap.put("success",true);
                    resultMap.put("msg","调用接口成功");
                    resultMap.put("data",jsonObject);
                    //数据库不存在sessionKey，则存入
                    if(configModelList.size() == 0){
                        ConfigModel insertConfigMOdel = new ConfigModel();
                        insertConfigMOdel.setConfigValue(jsonObject);
                        insertConfigMOdel.setType(1);
                        insertConfigMOdel.setCreateTime(new Date());
                        int i = configService.doInsertConfig(insertConfigMOdel);
                    }
                }else{
                    resultMap.put("success",false);
                    resultMap.put("msg",resultJson.get("RespMsg")+"");
                }
            }else{
                resultMap.put("success",false);
                resultMap.put("msg","调用接口失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("success",false);
            resultMap.put("msg","调用接口异常");
            return  resultMap;
        }
        return resultMap;
    }
    /**
     * @description:查询车辆信息接口
     * @auther: pengjien
     * @date: 2019/12/15 11:42
     * @Param VehPlateNo: 车牌号
     * @Param VehColorTypeCode: 车牌颜色类型
     * @Param CardNo:ETC卡号
     * @Param ObuNo:OBU卡号
     * @return: java.lang.Object
     **/
    public Map<String, Object> transferQueryETCCarInfo(String VehPlateNo, String VehColorTypeCode, String ObuNo,String sessionKey) {
        Map<String, Object> resultMap = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject requestJson = new JSONObject();
        requestJson.put("VehPlateNo",VehPlateNo);
        requestJson.put("VehColorTypeCode",VehColorTypeCode);
        requestJson.put("ObuNo",ObuNo);
        //每次请求必带值
        requestJson.put("ClientIP",clientIp);
        requestJson.put("SessionKey",sessionKey);
        String requestURL = url+"est/est05050.xhtml";
        HttpPost httpPost = new HttpPost(requestURL);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(180 * 1000)
                .setConnectTimeout(180 * 1000).setSocketTimeout(180 *1000).setRedirectsEnabled(false).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type","aplication/json");
        httpPost.setEntity(new StringEntity(requestJson.toJSONString(), ContentType.create("application/json","GBK")));
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                JSONObject resultJson = JSONObject.parseObject(result);
                //respCode等于0
                if(0 == Integer.parseInt(resultJson.get("RespCode")+"")){
                    resultMap.put("success",true);
                    resultMap.put("msg","调用接口成功");
                    resultMap.put("data",resultJson);
                }else{
                    resultMap.put("success",false);
                    resultMap.put("msg",resultJson.get("RespMsg")+"");
                }
            }else{
                resultMap.put("success",false);
                resultMap.put("msg","调用接口失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("success",false);
            resultMap.put("msg","调用接口异常");
            return  resultMap;
        }
        return resultMap;
    }



    /**
     * 定时通知发行解禁-调用解禁接口
     *
     * @param cardNo
     * @return
     */
    public CardLiftingDTO cardLifting(String cardNo) {
        String requestUrl = url + "/est/est03054.xhtml";
//        ConfigModel queryConfigModel = new ConfigModel();
//        queryConfigModel.setType(1);
//        List<ConfigModel> configModelList = configService.doFindConfigList(queryConfigModel);
        //会话秘钥
        String configValue = "";
        Map<String, Object> stringObjectMap = this.transferIssueLogin();
        if((boolean)stringObjectMap.get("success")){
            configValue = stringObjectMap.get("data").toString();
        }
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        CardLiftingQuery cardLiftingQuery = new CardLiftingQuery();
        cardLiftingQuery.setClientIP(clientIp);
        cardLiftingQuery.setSessionKey(configValue);
        cardLiftingQuery.setCardNo(cardNo);
        cardLiftingQuery.setIsCheck(false);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(cardLiftingQuery);
        //请求体
        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);
        //发起请求
        String jsonResult = restTemplate.postForObject(requestUrl, entity, String.class);
        CardLiftingDTO cardLiftingDTO = JSON.parseObject(jsonResult, new TypeReference<CardLiftingDTO>() {
        });
        return cardLiftingDTO;
    }

}
