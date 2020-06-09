package com.sutong.auditPastOrder.mapper;

import com.sutong.auditPastOrder.model.AuditPastOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 通行费差额汇总单管理
 * @author： Mr.Kong
 * @date: 2019/12/18 10:24
 */
@Repository
@Mapper
@Component
public interface AuditPastOrderMapper {

    /**
     * @description: 查询通行费差额汇总单分页
     * @auther: Mr.kong
     * @date: 2019/12/18 16:17
     * @Param auditPastOrder: 通行费差额汇总单实体
     * @return: java.util.List<com.sutong.auditPastOrder.model.AuditPastOrder>
     **/
    List<AuditPastOrder> doFindAuditPastOrderPage(AuditPastOrder auditPastOrder);

    int deleteByPrimaryKey(String pastOrderId);

    int insert(AuditPastOrder record);

    int insertSelective(AuditPastOrder record);

    AuditPastOrder selectByPrimaryKey(String pastOrderId);

    int updateByPrimaryKeySelective(AuditPastOrder record);

    int updateByPrimaryKey(AuditPastOrder record);

}
