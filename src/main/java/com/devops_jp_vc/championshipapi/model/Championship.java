package com.devops_jp_vc.championshipapi.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Championship {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    @OneToOne(mappedBy = "championship")
    private Table table;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
