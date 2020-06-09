package com.sutong.auditMainResult.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.auditMainResult.mapper.AuditMainResultMapper;
import com.sutong.auditMainResult.model.AuditMainResult;
import com.sutong.auditMainResult.service.AuditMainResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 稽核结论信息管理
 * @author： Mr.Kong
 * @date: 2019/12/15 15:10
 */
@Component
@Service
public class AuditMainResultServiceImpl implements AuditMainResultService {

    @Autowired
    private AuditMainResultMapper auditMainResultMapper;
    /**
     * @description: 查询稽核结论信息列表
     * @auther: Mr.kong
     * @date: 2019/12/15 17:17
     * @Param record: 稽核结论信息实体
     * @return: java.util.List<com.sutong.auditMainResult.model.AuditMainResult>
     **/
    @Override
    public List<AuditMainResult> doFindAuditMainResultList(AuditMainResult record) {
        return auditMainResultMapper.doFindAuditMainResultList(record);
    }

    /**
     * @description: 查询稽核结论信息分页
     * @auther: Mr.kong
     * @date: 2019/12/15 15:43
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @Param record: 稽核结论信息实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.auditMainResult.model.AuditMainResult>
     **/
    @Override
    public PageInfo<AuditMainResult> doFindAuditMainResultPage(int pageNum, int pageSize,AuditMainResult record) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<AuditMainResult> auditMainResultList = auditMainResultMapper.doFindAuditMainResultPage(record);
        PageInfo<AuditMainResult> pageInfo=new PageInfo<>(auditMainResultList);
        return pageInfo;
    }

    @Override
    public int deleteByPrimaryKey(String auditResultId) {
        return auditMainResultMapper.deleteByPrimaryKey(auditResultId);
    }

    @Override
    public int insert(AuditMainResult record) {
        return auditMainResultMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditMainResult record) {
        return auditMainResultMapper.insertSelective(record);
    }

    @Override
    public AuditMainResult selectByPrimaryKey(String auditResultId) {
        return auditMainResultMapper.selectByPrimaryKey(auditResultId);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditMainResult record) {
        return auditMainResultMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuditMainResult record) {
        return auditMainResultMapper.updateByPrimaryKey(record);
    }
}
