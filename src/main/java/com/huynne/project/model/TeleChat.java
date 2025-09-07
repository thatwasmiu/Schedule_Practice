package com.huynne.project.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tele_group")
public class TeleGroup {
    @Id
    private String id;

    private String name;

    private String description;
}
