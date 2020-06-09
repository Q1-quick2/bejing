package com.sutong.auditPastOrderInfo.mapper;

import com.sutong.auditPastOrderInfo.model.AuditPastOrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 通行费差额明细单管理
 * @author： Mr.Kong
 * @date: 2019/12/18 9:58
 */
@Repository
@Mapper
@Component
public interface AuditPastOrderInfoMapper {
    /**
     * @description: 查询通行费差额明细单列表
     * @auther: Mr.kong
     * @date: 2019/12/18 16:19
     * @Param transObuId: 交易时OBU号
     * @return: java.util.List<com.sutong.auditPastOrderInfo.model.AuditPastOrderInfo>
     **/
    List<AuditPastOrderInfo> doFindAuditPastOrderInfoPage(AuditPastOrderInfo record);

    int deleteByPrimaryKey(String id);

    int insert(AuditPastOrderInfo record);

    int insertSelective(AuditPastOrderInfo record);

    AuditPastOrderInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuditPastOrderInfo record);

    int updateByPrimaryKey(AuditPastOrderInfo record);
}
