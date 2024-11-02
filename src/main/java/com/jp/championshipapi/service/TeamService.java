package com.jp.championshipapi.service;

import com.jp.championshipapi.model.Player;
import com.jp.championshipapi.model.Team;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    List<Team> findAll();
    Team findById(UUID id);
    Team create(Team team);
    Team addPlayer(Player player, Team team);
    void removePlayer(Player player, Team team);
}
