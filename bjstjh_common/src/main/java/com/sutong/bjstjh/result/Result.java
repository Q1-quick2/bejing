package com.sutong.bjstjh.result;

import java.util.HashMap;
import java.util.Map;

/* 返回数据工具类 */
public class Result {

    private boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<String, Object>();

    private Result() {
    }

    public static Result ok() {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.OK);
        r.setMessage("成功");
        return r;
    }

    public static Result error() {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public static Result error2() {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("账号或密码错误");
        return r;
    }

    public static Result alreadyFee() {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.ALREADY_FEE);
        r.setMessage("已补费");
        return r;
    }

    /**
     * @param code 错误码，由ResultCode指定
     * @param errMsg 错误信息
     * @return 
     */
    public static Result error(Integer code,String errMsg) {
    	Result r = new Result();
    	r.setSuccess(false);
    	r.setCode(code);
    	r.setMessage(errMsg);
    	return r;
    }

    //返回对象本身，可以连续调用，输出多个信息
    public Result message(String message) {
        this.setMessage(message);
        return this;
    }


    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}