package com.sutong.auditCheckResults.servcie.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.auditCheckResults.mapper.AuditCheckResultsMapper;
import com.sutong.auditCheckResults.service.AuditCheckResultsService;
import com.sutong.bjstjh.entity.AuditCheckResults;
import com.sutong.bjstjh.util.enumerate.FileConfigEnum;
import com.sutong.mapper.FileProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AuditCheckResultsServiceImpl implements AuditCheckResultsService {

    @Autowired
    private AuditCheckResultsMapper auditCheckResultsMapper;

    @Autowired
    private FileProcessMapper fileProcessMapper;
    @Override
    public Integer doInsertAuditCheckResults(AuditCheckResults auditCheckResults) {
//      调用插入方法之前先执行删除，但是外键还保留原本的外键
        auditCheckResultsMapper.doDeleteAuditCheckResultsById(auditCheckResults.getMinistryWorkOrderForeignId());
//        获取系统时间set进createTime属性中插入数据库
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date =df.format(new Date());// new Date()为获取当前系统时间
        auditCheckResults.setCreateTime(date);
        Integer integer = auditCheckResultsMapper.doInsertAuditCheckResults(auditCheckResults);
        return integer;
    }

    //    稽核结论对比结果查询
    @Override
    public AuditCheckResults doFindAuditCheckResults(String ministryWorkOrderForeignId) {
        AuditCheckResults auditCheckResults = auditCheckResultsMapper.doFindAuditCheckResults(ministryWorkOrderForeignId);
        //        判断一下对象是否为空，为空直接返回空对象。否则就取出主键，然后根据主键图片文件表查出图片路径。
        if(StringUtils.isEmpty(auditCheckResults)){
            return auditCheckResults;
        }
        String checkResultsId = auditCheckResults.getCheckResultsId();
//      调用图片文件查询的Mapper然后传入表名和上一步取出的主键查出的list集合set进本个对象的图片路径的imgAdress属性
        List<String> list = fileProcessMapper.queryFilepath(FileConfigEnum.AUDIT_CHECK_RESULTS.toString(), checkResultsId);
        auditCheckResults.setImgAddress(list.toString());
        return auditCheckResults;
    }
}
