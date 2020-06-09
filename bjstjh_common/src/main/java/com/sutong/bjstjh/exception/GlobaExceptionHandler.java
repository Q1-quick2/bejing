package com.sutong.bjstjh.exception;


import com.sutong.bjstjh.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class GlobaExceptionHandler {

    //全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        System.err.print(Result.error().message("出错了"));
        return Result.error().message("出错了");
    }


    //特殊异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result arithmeticExceptionError(ArithmeticException e){
        e.printStackTrace();
        System.err.print(Result.error().message("出错了"));
        return Result.error().message("除数不能为0");
    }


    //自定义异常
    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public Result error(BaseException e){
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }





}
