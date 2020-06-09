package com.sutong.auditPayConfirmation;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.auditCodeTable.model.AuditCodeTable;
import com.sutong.auditCodeTable.service.AuditCodeTableService;
import com.sutong.auditPayConfirmation.model.AuditPayConfirmation;
import com.sutong.auditPayConfirmation.service.AuditPayConfirmationService;
import com.sutong.bjstjh.result.Result;
import com.sutong.bjstjh.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 补费确认单信息-接口管理
 * @author： Mr.Kong
 * @date: 2020/1/6 10:34
 */
@Api(tags = "补费确认单-接口管理")
@CrossOrigin
@RestController
public class AuditPayConfirmationController {

    private static final Logger logger = LoggerFactory.getLogger(AuditPayConfirmationController.class);
    private static final String CAR_CLASS="SYS_CAR_CLASS_TYPE";//车种
    private static final String CAR_TYPE="SYS_CAR_TYPE";//车型
    @Reference
    private AuditPayConfirmationService auditPayConfirmationService;
    @Reference
    private AuditCodeTableService auditCodeTableService;

    /**
     * @description: 查询补费确认单详情接口
     * @auther: Mr.kong
     * @date: 2020/1/6 10:46
     * @Param payId: 主键id
     * @return: com.sutong.bjstjh.result.Result
     **/
    @ApiOperation(value = "查询补费确认单详情接口")
    @GetMapping("/auditPayConfirmation/info")
    public Result doFindAuditPayConfirmationInfo(String payRfid){
        try{
            AuditPayConfirmation auditPayConfirmation = auditPayConfirmationService.doFindAuditPayConfirmationInfo(payRfid);
            return Result.ok().data("auditPayConfirmation",auditPayConfirmation);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询补费确认单详情接口失败！");
            return Result.error().message("查询补费确认单详情接口失败！");
        }
    }

    @ApiOperation(value = "查询补费确认单导出参数接口")
    @GetMapping("/auditPayConfirmation/export")
    public Result auditPayConfirmationExport(String payRfid){
        try{
            AuditPayConfirmation auditPayConfirmation = auditPayConfirmationService.doFindAuditPayConfirmationInfo(payRfid);
            //转义时间格式
            String payTime = auditPayConfirmation.getPayTime();
            auditPayConfirmation.setPayTime(auditPayConfirmationService.dateFormat(payTime));
            String payEndTime = auditPayConfirmation.getPayEndTime();
            auditPayConfirmation.setPayEndTime(auditPayConfirmationService.dateFormat(payEndTime));
            String payDate = auditPayConfirmation.getPayDate();
            auditPayConfirmation.setPayDate(auditPayConfirmationService.dateFormat(payDate));
            //转义车型
            String payCarType = auditPayConfirmation.getPayCarType();
            if (StringUtils.isNotEmpty(payCarType)) {
                AuditCodeTable nameByCodeAndGenName = auditCodeTableService.getNameByCodeAndGenName(payCarType,CAR_CLASS);
                payCarType = nameByCodeAndGenName.getName();
                auditPayConfirmation.setPayCarType(payCarType);
            }
            return Result.ok().data("auditPayConfirmation",auditPayConfirmation);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询补费确认单导出参数接口失败！");
            return Result.error().message("查询补费确认单导出参数接口失败！");
        }
    }

}
