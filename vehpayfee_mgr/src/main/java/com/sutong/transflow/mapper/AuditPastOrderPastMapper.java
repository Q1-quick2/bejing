package com.sutong.transflow.mapper;

import com.sutong.transflow.model.AuditPastOrderModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 冯敬博 on 2019/12/12.
 */

@Repository
@Mapper
@Component
public interface AuditPastOrderPastMapper {


    List<AuditPastOrderModel> getAuditPastOrderResultList(AuditPastOrderModel flowModel);

    AuditPastOrderModel getAuditPastOrderResult(AuditPastOrderModel flowModel);

}
