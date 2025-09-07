package com.huynne.project.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule_task")
public class ScheduleTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cronValue;

    private String description;

    private Boolean active;

    @ManyToMany
    @JoinTable(
            name = "chat_schedule",
            joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "chatId")
    )
    private List<TeleChat> chatList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCronValue() {
        return cronValue;
    }

    public void setCronValue(String cronValue) {
        this.cronValue = cronValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<TeleChat> getChatList() {
        return chatList;
    }

    public void setChatList(List<TeleChat> chatList) {
        this.chatList = chatList;
    }
}
