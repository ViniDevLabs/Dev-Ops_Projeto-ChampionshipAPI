package com.devops_jp_vc.championshipapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Game {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Team homeTeam;
    
    @ManyToOne
    private Team awayTeam;

    private int homeTeamGoals;
    private int awayTeamGoals;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;
}
