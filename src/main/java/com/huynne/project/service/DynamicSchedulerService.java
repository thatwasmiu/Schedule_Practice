package com.huynne.project.service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

@Service
public class DynamicSchedulerService {

    private final ThreadPoolTaskScheduler scheduler;
    private ScheduledFuture<?> scheduledTask;
    private String cronExpression = "*/5 * * * * *"; // mặc định 5s

    public DynamicSchedulerService(ThreadPoolTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void startTask() {
        stopTask(); // đảm bảo không có task cũ đang chạy song song.
        scheduledTask = scheduler.schedule(
                () -> System.out.println("Đứng dậy cho mắt đỡ mỏi " + LocalDateTime.now()),
                new CronTrigger(cronExpression)
        );
    }

    // Nếu có job đang chạy (scheduledTask != null), thì gọi cancel(false) để hủy job đó.
    // false nghĩa là không interrupt nếu task đang chạy dở.
    public void stopTask() {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }
    }

    // Cho phép cập nhật lại biểu thức cron trong runtime.
    public void updateCron(String newCron) {
        this.cronExpression = newCron;
        startTask();
    }
}


