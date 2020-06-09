package com.sutong.downloadWord.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.auditCodeTable.model.AuditCodeTable;
import com.sutong.auditCodeTable.service.AuditCodeTableService;
import com.sutong.auditPayConfirmation.model.AuditPayConfirmation;
import com.sutong.auditPayConfirmation.service.AuditPayConfirmationService;
import com.sutong.bjstjh.util.*;
import com.sutong.downloadWord.controller.tool.CustomXWPFDocument;
import com.sutong.downloadWord.controller.tool.WorderToNewWordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 导出 word
 * @author： Mr.Kong
 * @date: 2020/1/4 15:17
 */
@Api(tags = "导出word-接口管理")
@RestController
public class WordController {

    private static final Logger logger = LoggerFactory.getLogger(WordController.class);

    private static final String CAR_TYPE="SYS_CAR_TYPE";//车型
    private static final String CAR_CLASS="SYS_CAR_CLASS_TYPE";//车种
    private static final String ESCAPE_FEE_TYPE="SYS_ESCAPE_FEE_TYPE";//稽查逃费类型

    @Reference
    private AuditPayConfirmationService auditPayConfirmationService;
    @Reference
    private AuditCodeTableService auditCodeTableService;

    /**
     * @description: 导出高速公路通行费补费确认单word
     * @auther: Mr.kong
     * @date: 2020/1/5 17:36
     * @Param request: 参数
     * @Param response: 参数
     * @return: void
     **/
    @ApiOperation(value = "导出高速公路通行费补费确认单word")
    @GetMapping("/sureBook/export")
    public void exportWord( HttpServletResponse response,String payRfid) {
        //模板地址
        String templatesPath = Thread.currentThread().getContextClassLoader().getResource("templates/booktwo.docx").getPath();
        logger.info("templatesPath:" + templatesPath);
        //临时文件地址
        String expPath = Thread.currentThread().getContextClassLoader().getResource("templates/").getPath();
        //文件名称
        String fileName="高速公路通行费补费确认单.docx";
        logger.info("fileName:" + fileName);
        expPath = expPath + fileName;
        logger.info("expPath:" + expPath);
        try {
            Map<String, Object> data = getDocParameters(payRfid);
            CustomXWPFDocument doc = WorderToNewWordUtils.changWord(templatesPath, data/*,mapList,placeList*/);
            FileOutputStream fopts = new FileOutputStream(expPath);
            doc.write(fopts);
            fopts.close();
            FileUtils.download(response, new FileInputStream(expPath), fileName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出高速公路通行费补费确认单word失败！");
        } finally {
            delFileWord(expPath);
        }
    }

    /**
     * @description: 获取word导出数据信息
     * @auther: Mr.kong
     * @date: 2020/1/7 10:43
     * @Param payId: 主键id
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    private Map<String, Object> getDocParameters(String payRfid){
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try{
            AuditPayConfirmation audit = auditPayConfirmationService.doFindAuditPayConfirmationInfo(payRfid);
            if (ObjectUtils.isNotNull(audit)){
                //头部信息
                dataMap.put("number","编号："+ audit.getPayId());//编号
                dataMap.put("date", "处理时间："+audit.getPayDisposeTime());//处理时间
                dataMap.put("place","处理地点："+audit.getPayDisposeSite());//处理地点
                //基本信息
                dataMap.put("code",audit.getPayPlateNumber());//车牌号码
                //车型（轴)
                String payCarType = audit.getPayCarType();
                if (StringUtils.isNotEmpty(payCarType)){
                    AuditCodeTable nameByCodeAndGenName = auditCodeTableService.getNameByCodeAndGenName(payCarType,CAR_TYPE);
                    payCarType=nameByCodeAndGenName.getName();
                }
                dataMap.put("type", payCarType);//车型（轴)

                //车辆类别
                String payCarCategory = audit.getPayCarCategory();
                if (StringUtils.isNotEmpty(payCarCategory)){
                    AuditCodeTable nameByCodeAndGenName = auditCodeTableService.getNameByCodeAndGenName(payCarCategory,CAR_CLASS);
                    payCarCategory=nameByCodeAndGenName.getName();
                }
                dataMap.put("class", payCarCategory);//车辆类别

                dataMap.put("color", audit.getPayPlateNumberColor());//车牌颜色
                dataMap.put("owner",audit.getPayVehicleOwner());//车辆所有人
                dataMap.put("driver",audit.getPayDriverName());//驾驶员姓名
                dataMap.put("tags",audit.getPayRfid());//电子标签（表面号）
                dataMap.put("no", audit.getPayEngineNumber());//发动机号
                dataMap.put("id",audit.getPayDriverNumber());//驾驶员身份证号
                dataMap.put("car",audit.getPayCardNumber() );//非现金卡号/通行卡卡号
                dataMap.put("road",audit.getPaySite());//涉及路段与站点
                //相关事实
                //时间1
                String payTime = audit.getPayTime();
                Date date1 = DateUtils.strToDate(payTime);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(date1);
                dataMap.put("one", String.valueOf(calendar1.get(Calendar.YEAR)));
                dataMap.put("mouth1", String.valueOf(calendar1.get(Calendar.MONTH)+1));
                dataMap.put("day1",String.valueOf(calendar1.get(Calendar.DATE)));
                //时间2
                String payEndTime = audit.getPayEndTime();
                Date date2 = DateUtils.strToDate(payEndTime);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(date2);
                dataMap.put("two", String.valueOf(calendar2.get(Calendar.YEAR)));
                dataMap.put("mouth2",String.valueOf(calendar2.get(Calendar.MONTH)+1));
                dataMap.put("day2",String.valueOf(calendar2.get(Calendar.DATE)));
                //稽查逃费类型
                String payInfo = audit.getPayInfo();
                if (StringUtils.isNotEmpty(payInfo)){
                    AuditCodeTable nameByCodeAndGenName = auditCodeTableService.getNameByCodeAndGenName(payInfo,ESCAPE_FEE_TYPE);
                    payInfo=nameByCodeAndGenName.getName();
                }
                dataMap.put("reason",payInfo);
                dataMap.put("count",audit.getPayCount());
                //签名信息
                //图片，如果是多个图片，就新建多个map
                Map<String, Object> signature = new HashMap<String, Object>();
                signature.put("width", 150);
                signature.put("height", 50);
                signature.put("type", "jpg");
                String paySignatureName=audit.getAuditPayConfirmation();
                paySignatureName=paySignatureName.substring(paySignatureName.indexOf(",")+1);
                signature.put("content",WorderToNewWordUtils.inputStream2ByteArray(Base64Util.base64ToInputStream(paySignatureName), true));
                dataMap.put("signature", signature);
                dataMap.put("phone", audit.getPayPhone());//联系电话
                //时间3
                String payDate = audit.getPayDate();
                Date date3 = DateUtils.strToDate(payDate);
                Calendar calendar3 = Calendar.getInstance();
                calendar3.setTime(date3);
                dataMap.put("three",String.valueOf(calendar3.get(Calendar.YEAR)));
                dataMap.put("mouth3",String.valueOf(calendar3.get(Calendar.MONTH)+1));
                dataMap.put("day3", String.valueOf(calendar3.get(Calendar.DATE)));
                //补费情况
                dataMap.put("money1", audit.getPayPaypike());
                dataMap.put("total", audit.getPayTotal());
                dataMap.put("deal", audit.getPayConductor());
                dataMap.put("unit", audit.getPayProcessingUnit());
                //=========================================================================================================================
                String part1="相关事实：经核查，该车辆于"+dataMap.get("one")+"年"+dataMap.get("mouth1")+"月"+dataMap.get("day1")+"号" +
                        " 至"+dataMap.get("two")+"年"+dataMap.get("mouth2")+"月"+dataMap.get("day2")+"日期间，在北京市高速公路，" +
                        "因："+dataMap.get("reason")+"，拒交、逃交、少交车辆通行费，共计"+dataMap.get("count")+"次，事实清楚，证据确凿。";

                String part2="联系电话："+dataMap.get("phone")+"   "+dataMap.get("three")+"年"+dataMap.get("mouth3")+"月"+dataMap.get("day3")+"日";


                String part3="补费情况：现按规定补收该车辆通行费"+dataMap.get("money1")+"元，" +
                        "赔偿通行卡工本费0元收回通行卡0张。合计："+dataMap.get("total")+"（大写）。" +
                        "处理人："+dataMap.get("deal")+"  处理单位："+dataMap.get("unit");

                dataMap.put("four",part1);
                dataMap.put("five",part2);
                dataMap.put("six",part3);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取word导出数据信息失败！");
        }
        return dataMap;
    }

    /**
     * 删除零时生成的文件
     */
    public static void delFileWord(String expFile) {
        File file1 = new File(expFile);
        file1.delete();
    }
}
