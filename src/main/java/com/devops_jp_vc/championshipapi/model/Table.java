package com.devops_jp_vc.championshipapi.model;

import jakarta.persistence.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Entity(name = "championship_table")
public class Table {
    @Id
    private UUID id = UUID.randomUUID();
    @OneToMany(mappedBy = "table", fetch = FetchType.EAGER)
    private List<TableEntry> tableEntries;
    @OneToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public List<TableEntry> getTableEntries() {
        return tableEntries.stream()
                .sorted(Comparator.comparingInt(TableEntry::getPoints).reversed())
                .toList();
    }

    public void setTableEntries(List<TableEntry> tableEntries) {
        this.tableEntries = tableEntries;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }
}
