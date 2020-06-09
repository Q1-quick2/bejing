package com.sutong.auditPayConfirmation.mapper;

import com.sutong.auditPayConfirmation.model.AuditPayConfirmation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Description: 补费确认单信息-接口管理
 * @author： Mr.Kong
 * @date: 2020/1/6 10:21
 */
@Repository
@Mapper
@Component
public interface AuditPayConfirmationMapper {

    AuditPayConfirmation doFindAuditPayConfirmationInfo(String payRfid);

    int insert(AuditPayConfirmation record);

    int insertSelective(AuditPayConfirmation record);
}
