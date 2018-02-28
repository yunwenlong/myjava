package com.yunwenlong.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * @INFO  
 *   com.yunwenlong.controller
 * 2017年9月14日下午7:52:34
 * @author 云文龙
 * @version 1.0
 */
@EnableAutoConfiguration		
@ComponentScan(basePackages={"com.yunwenlong.controller","com.yunwenlong.common","com.yunwenlong.service"})   // 扫码控制器包
@MapperScan(basePackages={"com.yunwenlong.dao","com.yunwenlong.mapping"})
//@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
	
