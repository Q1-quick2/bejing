package com.sutong.pay.mapper;

import com.sutong.bjstjh.entity.NotificationModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by main on 2019/12/17.
 */
@Mapper
@Repository
public interface NotificationMapper {
    // 发送短信之后将结果保存数据库
    void doInsertNotification(NotificationModel notificationModel);
}
