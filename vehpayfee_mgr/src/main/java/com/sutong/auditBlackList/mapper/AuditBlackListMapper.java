package com.sutong.auditBlackList.mapper;

import com.sutong.auditBlackList.model.AuditBlackList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 黑名单管理
 * @author： Mr.Kong
 * @date: 2019/12/20 16:29
 */
@Repository
@Component
@Mapper
public interface AuditBlackListMapper {

    /**
     * @description: 查询黑名单分页
     * @auther: Mr.kong
     * @date: 2019/12/20 17:04
     * @Param auditBlackList: 黑名单实体
     * @return: java.util.List<com.sutong.auditBlackList.model.AuditBlackList>
     **/
    List<AuditBlackList> doFindAuditBlackListPage(AuditBlackList auditBlackList);

    int deleteByPrimaryKey(String blackListid);

    int insert(AuditBlackList record);

    int insertSelective(AuditBlackList record);

    AuditBlackList selectByPrimaryKey(String blackListid);

    int updateByPrimaryKeySelective(AuditBlackList record);

    int updateByPrimaryKey(AuditBlackList record);
}
