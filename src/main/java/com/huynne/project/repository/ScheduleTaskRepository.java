package com.huynne.project.repository;

import com.huynne.project.model.ScheduleTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleTaskRepository extends JpaRepository<ScheduleTask, Long> {

    List<ScheduleTask> findAllByActiveIsTrue();
}
