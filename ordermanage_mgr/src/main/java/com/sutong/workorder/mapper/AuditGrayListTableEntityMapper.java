package com.sutong.workorder.mapper;

import com.sutong.workorder.entity.AuditGrayListTableEntity;
import com.sutong.workorder.model.AuditGreyListSelectQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: AuditGrayListTableEntityMapper
 * @Description: 灰名单实体Mapper
 * @author: lichengquan
 * @date: 2019年12月19日 16:52
 * @Version: 1.0
 */
@Repository
@Mapper
public interface AuditGrayListTableEntityMapper {

    int insert(AuditGrayListTableEntity auditGrayListTableEntity);

    List<AuditGrayListTableEntity> selectByQuery(AuditGreyListSelectQuery auditGreyListSelectQuery);

    Integer batchInsertGrayList(List<AuditGrayListTableEntity> grayLists);
}
