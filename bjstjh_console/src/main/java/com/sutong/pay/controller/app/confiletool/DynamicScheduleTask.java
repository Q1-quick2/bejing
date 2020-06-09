package com.sutong.pay.controller.app.confiletool;

import com.sutong.bjstjh.util.StringUtils;
import com.sutong.pay.controller.app.AppAuditPayBackFeeFlowController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

/**
 * @Description:
 * @ClassName: ScheduleTask
 * @author： liyan
 * @date: 2020/1/8 15:52
 * @Version： 1.0
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DynamicScheduleTask implements SchedulingConfigurer {
//    @Value("${task.invoice55Time}")
    private String invoice55Time = "0 0/10 * ? * ?";
    @Autowired
    private AppAuditPayBackFeeFlowController controller;
    /**
     * 执行定时任务
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(
                ()->controller.getupdateInovice(),

                //1.添加任务内容(Runnable)
//                () -> System.out.println("执行动态定时任务发票同步本地: " + LocalDateTime.now().toLocalTime()),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = invoice55Time;
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }

}