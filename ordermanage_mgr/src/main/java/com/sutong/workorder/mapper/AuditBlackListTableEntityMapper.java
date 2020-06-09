package com.sutong.workorder.mapper;

import com.sutong.workorder.entity.AuditBlackListTableEntity;
import com.sutong.workorder.model.AuditBlackListSelectQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AuditBlackListTableEntityMapper
 * @Description: 黑名单实体Mapper
 * @author: lichengquan
 * @date: 2019年12月19日 11:17
 * @Version: 1.0
 */
@Repository
@Mapper
public interface AuditBlackListTableEntityMapper {

    Integer insert(AuditBlackListTableEntity auditBlackListTableEntity);

    List<AuditBlackListTableEntity> selectByQuery(AuditBlackListSelectQuery auditBlackListSelectQuery);

    Integer batchInsertBlackList(List<AuditBlackListTableEntity> blackLists);

    Integer updateBlackListById(AuditBlackListTableEntity auditBlackListTableEntity);

    Integer deleteBlackListById(AuditBlackListTableEntity auditBlackListTableEntity);

    Integer batchUpdateCaseWhen(List<AuditBlackListTableEntity> auditBlackListTableEntities);
}
