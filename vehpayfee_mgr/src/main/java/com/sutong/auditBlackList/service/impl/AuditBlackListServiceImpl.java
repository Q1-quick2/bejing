package com.sutong.auditBlackList.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sutong.auditBlackList.mapper.AuditBlackListMapper;
import com.sutong.auditBlackList.model.AuditBlackList;
import com.sutong.auditBlackList.service.AuditBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 黑名单管理
 * @author： Mr.Kong
 * @date: 2019/12/20 16:33
 */
@Component
@Service
public class AuditBlackListServiceImpl implements AuditBlackListService {

    @Autowired
    private AuditBlackListMapper auditBlackListMapper;
    /**
     * @description: 查询黑名单分页
     * @auther: Mr.kong
     * @date: 2019/12/20 17:03
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @Param auditBlackList: 黑名单实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.auditBlackList.model.AuditBlackList>
     **/
    @Override
    public PageInfo<AuditBlackList> doFindAuditBlackListPage(int pageNum, int pageSize, AuditBlackList auditBlackList) {
        PageHelper.startPage(pageNum,pageSize);
        List<AuditBlackList> auditBlackLists = auditBlackListMapper.doFindAuditBlackListPage(auditBlackList);
        PageInfo<AuditBlackList> pageInfo=new PageInfo<>(auditBlackLists);
        return pageInfo;
    }

    @Override
    public int deleteByPrimaryKey(String blackListid) {
        return auditBlackListMapper.deleteByPrimaryKey(blackListid);
    }

    @Override
    public int insert(AuditBlackList record) {
        return auditBlackListMapper.insert(record);
    }

    @Override
    public int insertSelective(AuditBlackList record) {
        return auditBlackListMapper.insertSelective(record);
    }

    @Override
    public AuditBlackList selectByPrimaryKey(String blackListid) {
        return auditBlackListMapper.selectByPrimaryKey(blackListid);
    }

    @Override
    public int updateByPrimaryKeySelective(AuditBlackList record) {
        return auditBlackListMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AuditBlackList record) {
        return auditBlackListMapper.updateByPrimaryKey(record);
    }
}
