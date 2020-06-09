package com.sutong;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* @EnableSwagger2
* */

@SpringBootApplication
@EnableDubboConfig
@EnableDubbo
//@EnableAutoConfiguration
public class BjstjhConsoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BjstjhConsoleApplication.class, args);
	}

}
