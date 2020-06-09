package com.sutong.service.impl;

import com.sutong.bjstjh.entity.LoginModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by main on 2020/1/13.
 */
@Mapper
@Repository
public interface LoginServiceMapper {

    @Select(" SELECT DISTINCT ACCOUNT as username , PASSWORD AS password FROM AUDIT_LOGIN_INFO WHERE ACCOUNT = #{account} AND PASSWORD = #{password}")
    LoginModel login(@Param("account") String account, @Param("password") String password);
}
