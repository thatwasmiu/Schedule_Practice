package com.huynne.project.model;

import javax.persistence.*;

@Entity
@Table(name = "tele_group")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cronValue;

    private String description;

    private Boolean active;
}
