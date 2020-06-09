package com.sutong.auditPayConfirmation.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sutong.auditPayConfirmation.mapper.AuditPayConfirmationMapper;
import com.sutong.auditPayConfirmation.model.AuditPayConfirmation;
import com.sutong.auditPayConfirmation.service.AuditPayConfirmationService;
import com.sutong.bjstjh.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @Description:
 * @author： Mr.Kong
 * @date: 2020/1/6 10:31
 */
@Component
@Service
public class AuditPayConfirmationServiceImpl implements AuditPayConfirmationService {

    @Autowired
    private AuditPayConfirmationMapper auditPayConfirmationMapper;

    public String dateFormat(String dateStr){
        Date date = DateUtils.strToDate(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String year=String.valueOf(calendar.get(Calendar.YEAR));
        String mouth=String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day=String.valueOf(calendar.get(Calendar.DATE));
        return year+"年"+mouth+"月"+day+"日";
    }


    @Override
    public AuditPayConfirmation doFindAuditPayConfirmationInfo(String payRfid) {
        return auditPayConfirmationMapper.doFindAuditPayConfirmationInfo(payRfid);
    }

    @Override
    public int insert(AuditPayConfirmation record) {
        return auditPayConfirmationMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditPayConfirmation record) {
        return auditPayConfirmationMapper.insertSelective(record);
    }
}
