package com.sutong.auditPastOrder.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.auditPastOrder.mapper.AuditPastOrderMapper;
import com.sutong.auditPastOrder.model.AuditPastOrder;
import com.sutong.auditPastOrder.service.AuditPastOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 通行费差额汇总单管理
 * @author： Mr.Kong
 * @date: 2019/12/18 10:25
 */
@Component
@Service
public class AuditPastOrderServiceImpl implements AuditPastOrderService {

    @Autowired
    private AuditPastOrderMapper auditPastOrderMapper;


    /**
     * @description: 查询通行费差额汇总单分页
     * @auther: Mr.kong
     * @date: 2019/12/18 16:16
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @Param auditPastOrder: 通行费差额汇总单实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.auditPastOrder.model.AuditPastOrder>
     **/
    @Override
    public PageInfo<AuditPastOrder> doFindAuditPastOrderPage(int pageNum, int pageSize, AuditPastOrder auditPastOrder) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<AuditPastOrder> auditPastOrders = auditPastOrderMapper.doFindAuditPastOrderPage(auditPastOrder);
        PageInfo<AuditPastOrder> pageInfo=new PageInfo<>(auditPastOrders);
        return pageInfo;
    }

    @Override
    public int deleteByPrimaryKey(String pastOrderId) {
        return auditPastOrderMapper.deleteByPrimaryKey(pastOrderId);
    }

    @Override
    public int insert(AuditPastOrder record) {
        return auditPastOrderMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditPastOrder record) {
        return auditPastOrderMapper.insertSelective(record);
    }

    @Override
    public AuditPastOrder selectByPrimaryKey(String pastOrderId) {
        return auditPastOrderMapper.selectByPrimaryKey(pastOrderId);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditPastOrder record) {
        return auditPastOrderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuditPastOrder record) {
        return auditPastOrderMapper.updateByPrimaryKey(record);
    }
}
