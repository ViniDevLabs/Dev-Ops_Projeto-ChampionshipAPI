package com.jp.championshipapi.service;

import com.jp.championshipapi.model.Game;
import com.jp.championshipapi.model.Team;

import java.util.List;
import java.util.UUID;

public interface GameService {
    Game create(Game game);
    Team play(Game game, int awayTeamGoals, int homeTeamGoals);
    void incrementGameCount(Team home, Team away);
    List<Game> teamsGames(Team team);
    Game findById(UUID id);
}
