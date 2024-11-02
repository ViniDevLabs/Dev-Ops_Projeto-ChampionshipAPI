package com.jp.championshipapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Player {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private int age;
    private String position;
    private int habilityPoints;
    private int gameCount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getHabilityPoints() {
        return habilityPoints;
    }

    public void setHabilityPoints(int habilityPoints) {
        this.habilityPoints = habilityPoints;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHability() {
        return habilityPoints;
    }

    public void setHability(int hability) {
        this.habilityPoints = hability;
    }
}
