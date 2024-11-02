package com.jp.championshipapi.service;

import com.jp.championshipapi.model.*;
import com.jp.championshipapi.repository.ChampionshipRepository;
import com.jp.championshipapi.repository.TableEntryRepository;
import com.jp.championshipapi.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChampionshipServiceImpl implements ChampionshipService {
    @Autowired
    private ChampionshipRepository championshipRepository;
    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private TableEntryRepository tableEntryRepository;

    @Override
    public Championship create(Championship championship) {
        return championshipRepository.save(championship);
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
    public List<TableEntry> getTable(Championship championship) {
         return championship.getTable().getTableEntries();
    }
}
