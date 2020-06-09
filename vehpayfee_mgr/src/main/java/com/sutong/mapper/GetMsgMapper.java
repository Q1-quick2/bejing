package com.sutong.mapper;

import com.sutong.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by 赵登壮 on 2019/12/12.
 */
@Repository
@Mapper
@Component
public interface GetMsgMapper {

    @Select("SELECT code as name FROM AUDIT_CODE_TABLE WHERE name = '河北省'")
    UserModel query();
}
