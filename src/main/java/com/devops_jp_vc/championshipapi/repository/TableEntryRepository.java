package com.devops_jp_vc.championshipapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devops_jp_vc.championshipapi.model.Table;
import com.devops_jp_vc.championshipapi.model.TableEntry;
import com.devops_jp_vc.championshipapi.model.Team;

import java.util.UUID;

public interface TableEntryRepository extends JpaRepository<TableEntry, UUID> {
    TableEntry findByTeamAndTable(Team team, Table table);
}
