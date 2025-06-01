package com.devops_jp_vc.championshipapi.service;

import java.util.List;
import java.util.UUID;

import com.devops_jp_vc.championshipapi.dto.TableEntryDTO;
import com.devops_jp_vc.championshipapi.model.*;

public interface ChampionshipService {
    Table create(Championship championship);

    Table updateTable(Table table, Team team, String result);

    List<TableEntryDTO> getTable(Championship championship);

    Championship findById(UUID id);

    TableEntryDTO addTeam(Team team, Championship championship);
}
