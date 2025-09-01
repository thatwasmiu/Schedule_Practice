package com.huynne.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {
    // Spring sẽ tìm key scheduler.poolSize trong file cấu hình application ( không bắt buộc có dòng về scheduler.poolSize )
    // Nếu có → lấy giá trị trong application.properties
    // Nếu không có → dùng giá trị mặc định sau dấu : (ở đây là 10).
    @Value("${scheduler.poolSize:10}")
    private Integer poolSize;

    @Bean
    public ThreadPoolTaskScheduler createThreadPoolTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        // Number of core threads in timed task execution thread pool
        scheduler.setPoolSize(poolSize);
        scheduler.setThreadNamePrefix("dynamic-scheduler-");
        scheduler.initialize();
        return scheduler;
    }
}
