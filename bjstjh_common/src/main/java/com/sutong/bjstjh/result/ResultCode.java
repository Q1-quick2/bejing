package com.sutong.bjstjh.result;

/* 返回数据码 */
public interface ResultCode {
    int OK = 20000; //成功
    int ERROR = 20001; //失败
    int DATA_ERROR = 20002; //失败,数据异常
    int USER_INF_ERROR = 20003; //用户检验失败
    int ALREADY_FEE = 10000;//已补费
}
