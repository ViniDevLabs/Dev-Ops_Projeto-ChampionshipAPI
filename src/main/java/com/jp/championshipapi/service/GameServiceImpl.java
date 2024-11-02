package com.jp.championshipapi.service;

import com.jp.championshipapi.model.Game;
import com.jp.championshipapi.model.Team;
import com.jp.championshipapi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game create(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Team play(Game game, int awayTeamGoals, int homeTeamGoals) {
        game.setAwayTeamGoals(awayTeamGoals);
        game.setHomeTeamGoals(homeTeamGoals);
        gameRepository.save(game);
        if(game.getAwayTeamGoals() > game.getHomeTeamGoals()) {
            return game.getAwayTeam();
        }
        return game.getHomeTeam();
    }

    @Override
    public List<Game> teamsGames(Team team) {
        return gameRepository.findAllByTeam(team);
    }
}
