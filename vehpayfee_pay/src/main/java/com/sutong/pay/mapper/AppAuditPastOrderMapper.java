package com.sutong.pay.mapper;

import com.sutong.bjstjh.entity.AppAuditPastOrderModel;
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
public interface AppAuditPastOrderMapper {

    /**
     * 查询历史补费流水列表集合
     * @param flowModel
     * @return
     */
    List<AppAuditPastOrderModel> getAppAuditPastOrderModelList(AppAuditPastOrderModel flowModel);

    /**
     * 查询历史补费流水详情信息
     * @param flowModel
     * @return
     */
    AppAuditPastOrderModel getAppAuditPastOrderInfoModel(AppAuditPastOrderModel flowModel);

}
