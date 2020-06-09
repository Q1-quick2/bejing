package com.sutong.aspectj;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sutong.bjstjh.entity.pay.LogModel;
import com.sutong.bjstjh.util.SnowflakeIdWorker;
import com.sutong.pay.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

//import lombok.extern.log4j.Log4j2;
/**
 * Created by main on 2019/12/24.
 */
@Component   //将类实列化到spring中的注解
@Aspect      //将它标识为一个切点类
public class LogAspectj {

    @Reference
    private LogService logService;


    Logger log = LoggerFactory.getLogger(LogAspectj.class);

    @Pointcut("execution(* com.sutong.pay.controller.app.AppRepairfeeController.AppDoFindRepairfeeEnd(..)) " +
              " || execution(* com.sutong.pay.controller.app.AppRepairfeeController.doConfirmationInfo(..)) " +
              " || execution(* com.sutong.pay.controller.app.AppRepairfeeController.AppPayEndInform(..)) " +
              " || execution(* com.sutong.pay.controller.app.AppPayController.appPayPreOrder(..))" +
              " || execution(* com.sutong.pay.controller.app.AppPayController.appPayPreOrderPast(..)) ")
    public static void getLog() {
    }

    //argNames 请求参数    returning 返回参数
    @AfterReturning(pointcut ="getLog()", returning = "obj")
    public void saveLog(JoinPoint joinpoint, Object obj) {
        LogModel logModel = new LogModel();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        // id
        logModel.setId(String.valueOf(idWorker.nextId()));
        //获取类名
        String className = joinpoint.getTarget().getClass().getSimpleName();
        logModel.setClassName(className);
        //获取方法名
        String methodName = joinpoint.getSignature().getName();
        logModel.setMethodName(methodName);
        //获取请求参数
        StringBuffer requesrParam = new StringBuffer();
        Object[] args = joinpoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            //append 追加
            requesrParam.append("第【").append(i+1).append("】参数=").append(args[i]);
        }
        logModel.setRequesrParam(requesrParam.toString());
        //获取返回参数
        logModel.setResponseParam(obj != null ? obj.toString() : "");
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        if(request != null) {
            //获取ip地址
            logModel.setIp(getIP(request,"1"));
            //获取用户id
            logModel.setUserId(getIP(request,"2"));
        }else{
            logModel.setIp("从request获取Session失败");
            logModel.setUserId("从request获取用户Session失败");
        }
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = formatter.format(new Date());
        logModel.setCreateTime(createTime);
        //将值存放到数据库 中
        try {
            logService.doAddAspectj(logModel);
        }catch (Exception e){
            log.error("<<<<<<<<  AOP切面日志异常 >>>>>>>");
        }
    }





    // 获取IP  获取用户ID
    public  String getIP(HttpServletRequest request,String str){
        if(str != null && !str.equals("") && Integer.parseInt(str) == 1){
            String ip=request.getHeader("x-forwarded-for");
            if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
                ip=request.getHeader("Proxy-Client-IP");
            }
            if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
                ip=request.getHeader("WL-Proxy-Client-IP");
            }
            if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
                ip=request.getHeader("X-Real-IP");
            }
            if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
                ip=request.getRemoteAddr();
            }
            return ip;
        }else if(str != null && !str.equals("") && Integer.parseInt(str) == 2){
                if(request.getSession().getAttribute("userId") != null){
                    String userId = (String) request.getSession().getAttribute("userId");
                    return userId;
                }else{
                    return "从Session中获取用户ID失败";
                }
        }else{
            log.error("===========获取request失败===========");
            return "获取request失败";
        }
    }










}
