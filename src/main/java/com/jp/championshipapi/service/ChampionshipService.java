package com.jp.championshipapi.service;

import com.jp.championshipapi.dto.TableEntryDTO;
import com.jp.championshipapi.model.*;

import java.util.List;
import java.util.UUID;

public interface ChampionshipService {
    Table create(Championship championship);
    Table updateTable(Table table, Team team, String result);
    List<TableEntryDTO> getTable(Championship championship);
    Championship findById(UUID id);
    TableEntryDTO addTeam(Team team, Championship championship);
}
