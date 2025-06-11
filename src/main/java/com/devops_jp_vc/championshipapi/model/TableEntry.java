package com.devops_jp_vc.championshipapi.model;

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
public class TableEntry {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Team team;
    
    @ManyToOne
    @JoinColumn(name = "championship_table_id")
    private Table table;

    private int points;
}
