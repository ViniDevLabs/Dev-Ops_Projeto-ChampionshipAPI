package com.jp.championshipapi.service;

import com.jp.championshipapi.model.Game;
import com.jp.championshipapi.model.Player;
import com.jp.championshipapi.model.TableEntry;
import com.jp.championshipapi.model.Team;
import com.jp.championshipapi.repository.GameRepository;
import com.jp.championshipapi.repository.PlayerRepository;
import com.jp.championshipapi.repository.TableEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TableEntryRepository tableEntryRepository;

    @Override
    public Game create(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Team play(Game game, int awayTeamGoals, int homeTeamGoals) {
        game.setAwayTeamGoals(awayTeamGoals);
        game.setHomeTeamGoals(homeTeamGoals);
        gameRepository.save(game);
        incrementGameCount(game.getHomeTeam(), game.getAwayTeam());
        setPoints(game);
        if(game.getAwayTeamGoals() > game.getHomeTeamGoals()) {
            return game.getAwayTeam();
        }
        return game.getHomeTeam();
    }

    @Override
    public void incrementGameCount(Team home, Team away) { //Add a game to all the involved players on the match
        List<Player> updatedHomePlayers = new ArrayList<>();
        for (Player p : home.getPlayerList()) {
            p.setGameCount(p.getGameCount() + 1);
            updatedHomePlayers.add(p);
        }
        playerRepository.saveAll(updatedHomePlayers);

        List<Player> updatedAwayPlayers = new ArrayList<>();
        for (Player p : away.getPlayerList()) {
            p.setGameCount(p.getGameCount() + 1);
            updatedAwayPlayers.add(p);
        }
        playerRepository.saveAll(updatedAwayPlayers);
    }

    @Override
    public void setPoints(Game game) {
        TableEntry homeRow = tableEntryRepository.findByTeamAndTable(game.getHomeTeam(), game.getChampionship().getTable());
        TableEntry awayRow = tableEntryRepository.findByTeamAndTable(game.getAwayTeam(), game.getChampionship().getTable());
        int homePoints = 0;
        int awayPoints = 0;
        if (game.getHomeTeamGoals() > game.getAwayTeamGoals()) {
            homePoints = 3;
        } else if (game.getHomeTeamGoals() == game.getAwayTeamGoals()) {
            homePoints = 1;
            awayPoints = 1;
        } else {
            awayPoints = 3;
        }
        homeRow.setPoints(homeRow.getPoints() + homePoints);
        awayRow.setPoints(awayRow.getPoints() + awayPoints);
        tableEntryRepository.save(homeRow);
        tableEntryRepository.save(awayRow);
    }

    @Override
    public List<Game> teamsGames(Team team) {
        return gameRepository.findAllByTeam(team);
    }

    @Override
    public Game findById(UUID id) {
        return gameRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }


}
