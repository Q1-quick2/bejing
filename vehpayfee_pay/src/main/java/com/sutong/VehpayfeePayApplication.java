package com.sutong;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableAutoConfiguration
@EnableDubbo
@MapperScan(value = "com.sutong")
@EnableTransactionManagement//开启事务
@EnableScheduling     // 定时器
public class VehpayfeePayApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehpayfeePayApplication.class, args);
	}

}
