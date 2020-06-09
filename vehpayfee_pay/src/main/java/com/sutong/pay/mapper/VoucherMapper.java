package com.sutong.pay.mapper;

import com.sutong.bjstjh.entity.VoucherModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by main on 2019/12/14.
 */
@Repository
@Mapper
public interface VoucherMapper {
    //客户补费凭证
    VoucherModel doRepairfeeVoucher(@Param("orderId") String orderId);
}
