package com.huynne.project.service;

import com.huynne.project.bot.BotRegister;
import com.huynne.project.bot.HuyenBot;
import com.huynne.project.bot.base.MessageSender;
import com.huynne.project.model.ScheduleTask;
import com.huynne.project.repository.ScheduleTaskRepository;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class DynamicSchedulerService {

    private final ThreadPoolTaskScheduler scheduler;
    Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    private String cronDefault = "*/500 * * * * *"; // mặc định 500s
    private final ScheduleTaskRepository scheduleTaskRepository;

    public DynamicSchedulerService(ThreadPoolTaskScheduler scheduler, ScheduleTaskRepository scheduleTaskRepository) {
        this.scheduler = scheduler;
        this.scheduleTaskRepository = scheduleTaskRepository;
    }


    public void startAllTask() {

        List<ScheduleTask> scheduleTasks = scheduleTaskRepository.findAllByActiveIsTrue();

        scheduleTasks.forEach(this::restartTask);
    }

    public void restartTask(ScheduleTask scheduleTask) {
        if (scheduledTasks.containsKey(scheduleTask.getId())) {
            scheduledTasks.get(scheduleTask.getId()).cancel(true); // stop scheduleTask cũ
        }
        MessageSender messageSender = BotRegister.messageSender;

        ScheduledFuture<?> future = scheduler.schedule(
                () -> scheduleTask.getChatList().forEach(
                        chat -> messageSender.sendTextMsg(chat.getChatId(), scheduleTask.getName())
                ), new CronTrigger(scheduleTask.getCronValue() != null ? scheduleTask.getCronValue() : cronDefault) // todo: valide cron nếu null hoặc không đúng dịnh dạng trả về giá trị default
        );
        scheduledTasks.put(scheduleTask.getId(), future);
    }

    @Transactional
    public ScheduleTask addTask(ScheduleTask scheduleTask) {
        ScheduleTask task = scheduleTaskRepository.save(scheduleTask);
        if (task.getActive()) {
            restartTask(task);
        }
        return task;
    }


    public void stopTask(Long id) {
        ScheduledFuture<?> future = scheduledTasks.get(id);
        if (future != null) {
            future.cancel(true);
        }
    }

    public boolean isValidTime(String cron) {
        return true; // todo: Validate cron
    }
}


