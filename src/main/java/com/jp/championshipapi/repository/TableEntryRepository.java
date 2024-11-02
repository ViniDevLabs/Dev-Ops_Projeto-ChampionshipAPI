package com.jp.championshipapi.repository;

import com.jp.championshipapi.model.Table;
import com.jp.championshipapi.model.TableEntry;
import com.jp.championshipapi.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TableEntryRepository extends JpaRepository<TableEntry, UUID> {
    TableEntry findByTeamAndTable(Team team, Table table);
}
