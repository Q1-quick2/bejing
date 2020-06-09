package com.sutong.auditDoorFrame.service;

import com.github.pagehelper.PageInfo;
import com.sutong.auditDoorFrame.model.AuditDoorFrame;

/**
 * @Description: 门架数据管理
 * @author： Mr.Kong
 * @date: 2019/12/23 11:27
 */
public interface AuditDoorFrameService {

    /**
     * @description: 查询门架数据分页
     * @auther: Mr.kong
     * @date: 2019/12/23 13:55
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @Param auditDoorFrame: 门架数据实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.auditDoorFrame.model.AuditDoorFrame>
     **/
    PageInfo<AuditDoorFrame> doFindAuditDoorFramePage(int pageNum,int pageSize,AuditDoorFrame auditDoorFrame);

    int deleteByPrimaryKey(String orderId);

    int insert(AuditDoorFrame record);

    int insertSelective(AuditDoorFrame record);

    AuditDoorFrame selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(AuditDoorFrame record);

    int updateByPrimaryKey(AuditDoorFrame record);
}
