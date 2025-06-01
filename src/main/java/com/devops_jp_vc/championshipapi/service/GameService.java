package com.devops_jp_vc.championshipapi.service;

import java.util.List;
import java.util.UUID;

import com.devops_jp_vc.championshipapi.model.Game;
import com.devops_jp_vc.championshipapi.model.Team;

public interface GameService {
    Game create(Game game);

    Team play(Game game, int awayTeamGoals, int homeTeamGoals);

    void incrementGameCount(Team home, Team away);

    void setPoints(Game game);

    List<Game> teamsGames(Team team);

    Game findById(UUID id);
}
