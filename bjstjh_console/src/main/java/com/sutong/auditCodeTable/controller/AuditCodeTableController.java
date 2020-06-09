package com.sutong.auditCodeTable.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.auditCodeTable.model.AuditCodeTable;
import com.sutong.auditCodeTable.service.AuditCodeTableService;
import com.sutong.bjstjh.result.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 字典数据管理
 * @author： Mr.Kong
 * @date: 2019/12/23 20:13
 */
@CrossOrigin
@RestController
public class AuditCodeTableController {

    @Reference
    private AuditCodeTableService auditCodeTableService;

    /**
     * @description: 查询字典列表信息
     * @auther: Mr.kong
     * @date: 2019/12/23 20:19
     * @Param generalName:
     * @return: com.sutong.bjstjh.result.Result
     **/
    @RequestMapping("/auditCode/list")
    public Result doFindAuditCodeList(@RequestParam("generalName")String generalName){
        try {
            List<AuditCodeTable> auditCodeTableList = auditCodeTableService.doFindAuditCodeTableList(generalName);
            return Result.ok().data("auditCodeTableList",auditCodeTableList);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error();
        }
    }
}
