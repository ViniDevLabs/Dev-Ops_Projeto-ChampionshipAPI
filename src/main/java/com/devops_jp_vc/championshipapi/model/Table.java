package com.devops_jp_vc.championshipapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "championship_table")
public class Table {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @OneToMany(mappedBy = "table", fetch = FetchType.EAGER)
    @Singular
    private List<TableEntry> tableEntries;
    
    @OneToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;

    public List<TableEntry> getTableEntries() {
        return tableEntries.stream()
                .sorted(Comparator.comparingInt(TableEntry::getPoints).reversed())
                .toList();
    }
}
