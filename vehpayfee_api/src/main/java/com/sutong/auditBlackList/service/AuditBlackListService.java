package com.sutong.auditBlackList.service;

import com.github.pagehelper.PageInfo;
import com.sutong.auditBlackList.model.AuditBlackList;

/**
 * @Description: 黑名单管理
 * @author： Mr.Kong
 * @date: 2019/12/20 16:33
 */
public interface AuditBlackListService {

    /**
     * @description: 查询黑名单分页
     * @auther: Mr.kong
     * @date: 2019/12/20 17:03
     * @Param pageNum: 当前页码
     * @Param pageSize: 分页条数
     * @Param auditBlackList: 黑名单实体
     * @return: com.github.pagehelper.PageInfo<com.sutong.auditBlackList.model.AuditBlackList>
     **/
    PageInfo<AuditBlackList> doFindAuditBlackListPage(int pageNum,int pageSize,AuditBlackList auditBlackList);

    int deleteByPrimaryKey(String blackListid);

    int insert(AuditBlackList record);

    int insertSelective(AuditBlackList record);

    AuditBlackList selectByPrimaryKey(String blackListid);

    int updateByPrimaryKeySelective(AuditBlackList record);

    int updateByPrimaryKey(AuditBlackList record);
}
