package com.devops_jp_vc.championshipapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Player {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;
    private int age;
    private String position;
    private int habilityPoints;
    private int gameCount;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;
}
