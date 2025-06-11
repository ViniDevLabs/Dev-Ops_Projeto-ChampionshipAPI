package com.devops_jp_vc.championshipapi.service.impl;

import org.springframework.stereotype.Service;

import com.devops_jp_vc.championshipapi.dto.TableEntryDTO;
import com.devops_jp_vc.championshipapi.model.*;
import com.devops_jp_vc.championshipapi.repository.ChampionshipRepository;
import com.devops_jp_vc.championshipapi.repository.TableEntryRepository;
import com.devops_jp_vc.championshipapi.repository.TableRepository;
import com.devops_jp_vc.championshipapi.service.ChampionshipService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChampionshipServiceImpl implements ChampionshipService {
    private final ChampionshipRepository championshipRepository;
    private final TableRepository tableRepository;
    private final TableEntryRepository tableEntryRepository;

    @Override
    public Table create(Championship championship) {
        Table table = new Table();
        table.setChampionship(championship);
        championship.setTable(table);
        championshipRepository.save(championship);
        return tableRepository.save(table);
    }

    @Override
    public Table updateTable(Table table, Team team, String result) {
        TableEntry tableEntry = tableEntryRepository.findByTeamAndTable(team, table);
        if (result.equals("won")) {
            tableEntry.setPoints(tableEntry.getPoints() + 3);
        }
        if (result.equals("draw")) {
            tableEntry.setPoints(tableEntry.getPoints() + 1);
        }

        return tableRepository.save(table);
    }

    @Override
    public List<TableEntryDTO> getTable(Championship championship) {
        return championship.getTable().getTableEntries().stream()
                .map(te -> (new TableEntryDTO(te.getTeam().getName(), te.getPoints()))).toList();
    }

    @Override
    public Championship findById(UUID id) {
        return championshipRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public TableEntryDTO addTeam(Team team, Championship championship) {
        TableEntry tableEntry = TableEntry.builder()
                .points(0)
                .table(championship.getTable())
                .team(team)
                .build();

        tableEntryRepository.save(tableEntry);
        championship.getTable().getTableEntries().add(tableEntry);
        return new TableEntryDTO(team.getName(), 0);
    }
}
