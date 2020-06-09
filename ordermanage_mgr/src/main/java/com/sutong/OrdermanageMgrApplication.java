package com.sutong;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableAutoConfiguration
@EnableDubbo
@MapperScan(value = "com.sutong")
public class OrdermanageMgrApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdermanageMgrApplication.class, args);
	}

}
