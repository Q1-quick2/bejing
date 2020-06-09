package com.sutong.auditDoorFrame.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.auditDoorFrame.mapper.AuditDoorFrameMapper;
import com.sutong.auditDoorFrame.model.AuditDoorFrame;
import com.sutong.auditDoorFrame.service.AuditDoorFrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 门架数据管理
 * @author： Mr.Kong
 * @date: 2019/12/23 11:28
 */
@Component
@Service
public class AuditDoorFrameServiceImpl implements AuditDoorFrameService {

    @Autowired
    private AuditDoorFrameMapper auditDoorFrameMapper;
    /**
     * @description: 查询门架数据分页
     * @auther: Mr.kong
     * @date: 2019/12/23 13:55
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @Param auditDoorFrame: 门架数据实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.auditDoorFrame.model.AuditDoorFrame>
     **/
    @Override
    public PageInfo<AuditDoorFrame> doFindAuditDoorFramePage(int pageNum,int pageSize,AuditDoorFrame auditDoorFrame) {
        PageHelper.startPage(pageNum,pageSize);
        List<AuditDoorFrame> auditDoorFrames = auditDoorFrameMapper.doFindAuditDoorFramePage(auditDoorFrame);
        PageInfo<AuditDoorFrame> pageInfo=new PageInfo<>(auditDoorFrames);
        return pageInfo;
    }

    @Override
    public int deleteByPrimaryKey(String orderId) {
        return auditDoorFrameMapper.deleteByPrimaryKey(orderId);
    }

    @Override
    public int insert(AuditDoorFrame record) {
        return auditDoorFrameMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditDoorFrame record) {
        return auditDoorFrameMapper.insertSelective(record);
    }

    @Override
    public AuditDoorFrame selectByPrimaryKey(String orderId) {
        return auditDoorFrameMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditDoorFrame record) {
        return auditDoorFrameMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuditDoorFrame record) {
        return auditDoorFrameMapper.updateByPrimaryKey(record);
    }
}
