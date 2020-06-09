package com.sutong;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sutong.service")
@EnableDubbo
public class VehpayfeeMgrApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehpayfeeMgrApplication.class, args);
	}

}
