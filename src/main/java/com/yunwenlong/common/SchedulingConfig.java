package com.yunwenlong.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 
 * @INFO  定时任务配置类
 *   com.ywl.common
 * 2017年9月14日下午8:50:24
 * @author 云文龙
 * @version 1.0
 */
@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {

	int count = 0;
    @Scheduled(cron = "0 0 0 * * ? ") // 每日00:00执行任务！参考网站	http://cron.qqe2.com/
//    @Scheduled(fixedRate=3000)				// 每3秒执行一次
    public void scheduler() {
       System.out.println("这是第" + ++count + "次执行");
    }

}