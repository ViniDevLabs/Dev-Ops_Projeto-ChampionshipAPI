package com.jp.championshipapi.service;

import com.jp.championshipapi.dto.TableEntryDTO;
import com.jp.championshipapi.model.*;
import com.jp.championshipapi.repository.ChampionshipRepository;
import com.jp.championshipapi.repository.TableEntryRepository;
import com.jp.championshipapi.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChampionshipServiceImpl implements ChampionshipService {
    @Autowired
    private ChampionshipRepository championshipRepository;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private TableEntryRepository tableEntryRepository;

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
        if(result.equals("won")) {
            tableEntry.setPoints(tableEntry.getPoints() + 3);
        }
        if(result.equals("draw")) {
            tableEntry.setPoints(tableEntry.getPoints() + 1);
        }

        return tableRepository.save(table);
    }

    @Override
    public List<TableEntryDTO> getTable(Championship championship) {
         return championship.getTable().getTableEntries().stream().map(te -> (new TableEntryDTO(te.getTeam().getName(), te.getPoints()))).collect(Collectors.toList());
    }

    @Override
    public Championship findById(UUID id) {
        return championshipRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public TableEntryDTO addTeam(Team team, Championship championship) {
        TableEntry tableEntry = new TableEntry();
        tableEntry.setPoints(0);
        tableEntry.setTable(championship.getTable());
        tableEntry.setTeam(team);
        tableEntryRepository.save(tableEntry);
        championship.getTable().getTableEntries().add(tableEntry);
        return new TableEntryDTO(team.getName(), 0);
    }
}
