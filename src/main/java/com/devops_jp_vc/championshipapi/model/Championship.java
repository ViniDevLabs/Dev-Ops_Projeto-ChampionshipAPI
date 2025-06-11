package com.devops_jp_vc.championshipapi.model;

import jakarta.persistence.*;
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
public class Championship {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;

    @OneToOne(mappedBy = "championship")
    private Table table;
}
