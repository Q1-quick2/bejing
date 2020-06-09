package com.sutong.pay.mapper;

import com.sutong.bjstjh.entity.AuditPaybackFeeFlowModel;
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
public interface AuditPaybackFeeFlowMapper {

    /**
     * 根据条件查询历史补费列表
     * @param flowModel
     * @return
     */
    List<AuditPaybackFeeFlowModel> paybackfeeFlow(AuditPaybackFeeFlowModel flowModel);

    /**
     * 根据主键查询历史补费详情
     * @param flowModel
     * @return
     */
    AuditPaybackFeeFlowModel paybackfeeFlowDetail(AuditPaybackFeeFlowModel flowModel);


}
