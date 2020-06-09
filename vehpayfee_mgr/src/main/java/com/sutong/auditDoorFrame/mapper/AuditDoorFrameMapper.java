package com.sutong.auditDoorFrame.mapper;

import com.sutong.auditDoorFrame.model.AuditDoorFrame;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 门架数据管理
 * @author： Mr.Kong
 * @date: 2019/12/23 11:26
 */
@Repository
@Component
@Mapper
public interface AuditDoorFrameMapper {

    /**
     * @description: 查询门架数据分页
     * @auther: Mr.kong
     * @date: 2019/12/23 13:55
     * @Param auditDoorFrame: 门架数据实体
     * @return: java.util.List<com.sutong.auditDoorFrame.model.AuditDoorFrame>
     **/
    List<AuditDoorFrame> doFindAuditDoorFramePage(AuditDoorFrame auditDoorFrame);

    int deleteByPrimaryKey(String orderId);

    int insert(AuditDoorFrame record);

    int insertSelective(AuditDoorFrame record);

    AuditDoorFrame selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(AuditDoorFrame record);

    int updateByPrimaryKey(AuditDoorFrame record);

}
