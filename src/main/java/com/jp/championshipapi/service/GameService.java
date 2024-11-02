package com.jp.championshipapi.service;

import com.jp.championshipapi.model.Game;
import com.jp.championshipapi.model.Team;

import java.util.List;

public interface GameService {
    Game create(Game game);
    Team play(Game game, int awayTeamGoals, int homeTeamGoals);
    List<Game> teamsGames(Team team);
}
