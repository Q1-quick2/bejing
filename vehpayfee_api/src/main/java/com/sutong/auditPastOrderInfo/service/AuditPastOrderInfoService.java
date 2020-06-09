package com.sutong.auditPastOrderInfo.service;

import com.github.pagehelper.PageInfo;
import com.sutong.auditPastOrderInfo.model.AuditPastOrderInfo;

/**
 * @Description: 通行费差额明细单管理
 * @author： Mr.Kong
 * @date: 2019/12/18 10:01
 */
public interface AuditPastOrderInfoService {

    /**
     * @description: 查询通行费差额明细单列表
     * @auther: Mr.kong
     * @date: 2019/12/18 16:19
     * @Param transObuId: 交易时OBU号
     * @return: java.util.List<com.sutong.auditPastOrderInfo.model.AuditPastOrderInfo>
     **/
    PageInfo<AuditPastOrderInfo> doFindAuditPastOrderInfoPage(AuditPastOrderInfo record, int pageNUm, int pageSize);

    int deleteByPrimaryKey(String id);

    int insert(AuditPastOrderInfo record);

    int insertSelective(AuditPastOrderInfo record);

    AuditPastOrderInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuditPastOrderInfo record);

    int updateByPrimaryKey(AuditPastOrderInfo record);

}
