package com.sutong.auditIssueVeh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.auditIssueVeh.mapper.AuditIssueVehMapper;
import com.sutong.auditIssueVeh.service.AuditIssueVehService;
import com.sutong.bjstjh.entity.AuditIssueVeh;
import com.sutong.bjstjh.util.enumerate.FileConfigEnum;
import com.sutong.bjstjh.util.enumerate.TableColumnEnum;
import com.sutong.mapper.FileProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AuditIssueVehServiceImpl implements AuditIssueVehService {
    @Autowired
    private AuditIssueVehMapper auditIssueVehMapper;

    @Autowired
    private FileProcessMapper fileProcessMapper;
    @Override
    public Integer doInsertAuditIssueVeh(AuditIssueVeh auditIssueVeh){

//        调用插入方法之前先执行删除，还保留原来的外键
        auditIssueVehMapper.doDeleteAuditIssueVehById(auditIssueVeh.getMinistryWorkOrderForeignId());
        Integer integer = auditIssueVehMapper.doInsertAuditIssueVeh(auditIssueVeh);
        return integer;
    }
//  发行车辆信息查询
    @Override
    public AuditIssueVeh doFindAuditIssueVeh(String ministryWorkOrderForeignId) {
//        根据发行车辆信息的外键去获取本条数据
        AuditIssueVeh auditIssueVeh =auditIssueVehMapper.doFindAuditIssueVeh(ministryWorkOrderForeignId);
        //        判断一下对象是否为空，为空直接返回空对象。否则就取出主键，然后根据主键图片文件表查出图片路径。
        if(StringUtils.isEmpty(auditIssueVeh)){
            return auditIssueVeh;
        }
        String issueVehId =auditIssueVeh.getIssueVehId();
        //      调用图片文件查询的Mapper然后传入表名和字段名以及上一步取出的主键查出两个list集合set进本个对象的两个图片路径的两个属性
        List<String> vehImgAddress=fileProcessMapper.queryFilepathByColumn(FileConfigEnum.AUDIT_ISSUE_VEH_TABLE.toString(),issueVehId.toString(), TableColumnEnum.VEH_IMG_ADDRESS.toString());
        List<String> drivImgAddress=fileProcessMapper.queryFilepathByColumn(FileConfigEnum.AUDIT_ISSUE_VEH_TABLE.toString(),issueVehId.toString(), TableColumnEnum.DRIV_IMG_ADDRESS.toString());
               auditIssueVeh.setVehImgAddress(vehImgAddress.toString());
               auditIssueVeh.setDrivImgAddress(drivImgAddress.toString());
               return auditIssueVeh;
    }
}
