package com.devops_jp_vc.championshipapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Team {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Player> playerList = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TableEntry> tableEntries = new ArrayList<>();
    private int rankingPoints;
}
