package com.jp.championshipapi.service;

import com.jp.championshipapi.model.*;

import java.util.List;

public interface ChampionshipService {
    Championship create(Championship championship);
    Table updateTable(Table table, Team team, String result);
    List<TableEntry> getTable(Championship championship);
}
