package com.sutong.historyNotice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.dodgingtoll.model.AuditPastOrder;
import com.sutong.historyNotice.mapper.HistoryNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: HistoryNoticeServiceImpl
 * @Description
 * @Author: Mr.Su
 * @Date: 2019/12/18 15:16
 * @Version V1.0
 **/
@Component
@Service
public class HistoryNoticeServiceImpl implements HistoryNoticeService{
    @Autowired
    private HistoryNoticeMapper historyNoticeMapper;

    @Override
    public int doInsertHistoryNoticeSelective(AuditPastOrder auditPastOrder) {
        return historyNoticeMapper.doInsertHistoryNoticeSelective(auditPastOrder);
    }

    @Override
    public int doInsertHistoryNotice(AuditPastOrder auditPastOrder) {
        return historyNoticeMapper.doInsertHistoryNotice(auditPastOrder);
    }

    @Override
    public List<AuditPastOrder> doFindAuditPastOrderList(AuditPastOrder auditPastOrder) {
        return historyNoticeMapper.doFindAuditPastOrderList(auditPastOrder);
    }

    @Override
    public List<AuditPastOrder> doFindAuditPastOrderAutoList(AuditPastOrder auditPastOrder) {
        return historyNoticeMapper.doFindAuditPastOrderAutoList(auditPastOrder);
    }

    @Override
    public PageInfo<AuditPastOrder> doFindAuditPastOrderPage(int pageNum, int pageSize, AuditPastOrder auditPastOrder) {
        PageHelper.startPage(pageNum, pageSize);
        List<AuditPastOrder> auditPastOrderList = historyNoticeMapper.doFindAuditPastOrderPage(auditPastOrder);
        PageInfo<AuditPastOrder> pageInfo=new PageInfo<>(auditPastOrderList);
        return pageInfo;
    }
}
