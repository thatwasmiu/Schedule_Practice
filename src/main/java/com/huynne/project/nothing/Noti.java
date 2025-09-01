package com.huynne.project.nothing;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Noti {
    @Scheduled(cron = "*/5 * * * * *")
    public void noti() {
        System.out.println("Đứng dậy cho mắt đỡ mỏi");
    }
}
