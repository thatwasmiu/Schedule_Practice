package com.huynne.project.endpoint;

import com.huynne.project.service.DynamicSchedulerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
public class SchedulerEndpoint {
    private final DynamicSchedulerService schedulerService;

    public SchedulerEndpoint(DynamicSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
        schedulerService.startTask(); // start mặc định khi app khởi động
    }

    @PostMapping("/update")
    public String update(@RequestParam String cron) {
        schedulerService.updateCron(cron);
        return "Updated cron to: " + cron;
    }

    @PostMapping("/stop")
    public String stop() {
        schedulerService.stopTask();
        return "Task stopped!";
    }

    @PostMapping("/start")
    public String start() {
        schedulerService.startTask();
        return "Task started!";
    }
}
