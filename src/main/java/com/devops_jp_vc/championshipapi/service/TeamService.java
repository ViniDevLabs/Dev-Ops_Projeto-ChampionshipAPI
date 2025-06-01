package com.devops_jp_vc.championshipapi.service;

import java.util.List;
import java.util.UUID;

import com.devops_jp_vc.championshipapi.model.Player;
import com.devops_jp_vc.championshipapi.model.Team;

public interface TeamService {
    List<Team> findAll();

    Team findById(UUID id);

    Team create(Team team);

    Team addPlayer(Player player, Team team);

    Team removePlayer(Player player, Team team);
}
