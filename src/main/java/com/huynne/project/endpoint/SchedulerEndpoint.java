package com.huynne.project.endpoint;

import com.huynne.project.model.ScheduleTask;
import com.huynne.project.service.DynamicSchedulerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduler")
public class SchedulerEndpoint {
    private final DynamicSchedulerService schedulerService;

    public SchedulerEndpoint(DynamicSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping("/update")
    public ScheduleTask update(@RequestBody ScheduleTask scheduleTask) {
        return schedulerService.addTask(scheduleTask);
    }

    @PostMapping("/stop")
    public String stop(@RequestParam("id") Long id) {
        schedulerService.stopTask(id);
        return "Task stopped!";
    }
}
