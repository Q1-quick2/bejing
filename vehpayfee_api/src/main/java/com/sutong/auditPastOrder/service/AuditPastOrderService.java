package com.sutong.auditPastOrder.service;

import com.github.pagehelper.PageInfo;
import com.sutong.auditPastOrder.model.AuditPastOrder;

/**
 * @Description: 通行费差额汇总单管理
 * @author： Mr.Kong
 * @date: 2019/12/18 9:51
 */
public interface AuditPastOrderService {

    /**
     * @description: 查询通行费差额汇总单分页
     * @auther: Mr.kong
     * @date: 2019/12/18 16:16
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @Param auditPastOrder: 通行费差额汇总单实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.auditPastOrder.model.AuditPastOrder>
     **/
    PageInfo<AuditPastOrder> doFindAuditPastOrderPage(int pageNum, int pageSize, AuditPastOrder auditPastOrder);

    int deleteByPrimaryKey(String pastOrderId);

    int insert(AuditPastOrder record);

    int insertSelective(AuditPastOrder record);

    AuditPastOrder selectByPrimaryKey(String pastOrderId);

    int updateByPrimaryKeySelective(AuditPastOrder record);

    int updateByPrimaryKey(AuditPastOrder record);
}
