package com.sutong.auditManualMinistryWorkOrder.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.auditManualMinistryWorkOrder.mapper.AuditManualMinistryWorkOrderMapper;
import com.sutong.auditManualMinistryWorkOrder.model.AuditManualMinistryWorkOrderModel;
import com.sutong.auditManualMinistryWorkOrder.service.AuditManualMinistryWorkOrderService;
import com.sutong.bjstjh.entity.AuditManualMinistryWorkOrder;
import com.sutong.bjstjh.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AuditManualMinistryWorkOrderServiceImpl implements AuditManualMinistryWorkOrderService {

    @Autowired
    private AuditManualMinistryWorkOrderMapper auditManualMinistryWorkOrderMapper;
//    部级工单创建保存
    @Override
    public Integer doInsertAuditManualMinistryWorkOrder(AuditManualMinistryWorkOrder auditManualMinistryWorkOrder) {

//        生成24位UUID大写主键
        String s = StringUtils.generateUUID();
        auditManualMinistryWorkOrder.setMinistryWorkOrderId(s);
//        获取系统时间set进createTime属性中插入数据库
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date =df.format(new Date());// new Date()为获取当前系统时间
        auditManualMinistryWorkOrder.setCreateTime(date);
        Integer integer =auditManualMinistryWorkOrderMapper.doInsertAuditManualMinistryWorkOrder(auditManualMinistryWorkOrder);
        return integer;
    }
//  部级工单条件查询

    @Override
    public PageInfo<AuditManualMinistryWorkOrder> doFindAuditManualMinistryWorkOrder(AuditManualMinistryWorkOrderModel auditManualMinistryWorkOrderModel) {
        PageHelper.startPage(auditManualMinistryWorkOrderModel.getPageIndex(), auditManualMinistryWorkOrderModel.getPageSize());

        List<AuditManualMinistryWorkOrder> list = auditManualMinistryWorkOrderMapper.doFindAuditManualMinistryWorkOrderList(auditManualMinistryWorkOrderModel);
        PageInfo<AuditManualMinistryWorkOrder> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
//  部级工单查询
    @Override
    public AuditManualMinistryWorkOrder doFindAuditManualMinistryWorkOrder(String ministryWorkOrderId) {
        return auditManualMinistryWorkOrderMapper.doFindAuditManualMinistryWorkOrder(ministryWorkOrderId);
    }
}
