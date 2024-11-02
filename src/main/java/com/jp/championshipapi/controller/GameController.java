package com.jp.championshipapi.controller;

import com.jp.championshipapi.dto.*;
import com.jp.championshipapi.model.Game;
import com.jp.championshipapi.model.Team;
import com.jp.championshipapi.service.GameService;
import com.jp.championshipapi.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private TeamService teamService;

    @PostMapping
    @Operation(summary = "Schedule a game", description = "Schedule a game between two teams on a specific date")
    public ResponseEntity<GameDTO> scheduleGame(@RequestBody GameToScheduleDTO gameToScheduleDTO) {
        Game newGame = new Game();
        newGame.setDate(gameToScheduleDTO.date());
        Team homeTeam = teamService.findById(gameToScheduleDTO.homeTeamId());
        Team awayTeam = teamService.findById(gameToScheduleDTO.awayTeamId());
        if(homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException();
        }
        newGame.setHomeTeam(homeTeam);
        newGame.setAwayTeam(awayTeam);
        Game createdGame = gameService.create(newGame);
        GameDTO gameDTO = new GameDTO(createdGame.getDate(), createdGame.getHomeTeam().getName(), createdGame.getAwayTeam().getName());
        return ResponseEntity.ok(gameDTO);
    }

    @PostMapping("/play/{id}")
    @Operation(summary = "Play an already scheduled game", description = "Give the result")
    public ResponseEntity<TeamDTO> play(@PathVariable UUID id, @RequestBody GameResultDTO gameResultDTO) {
        Game scheduledGame = gameService.findById(id);
        if(scheduledGame == null) {
            throw new IllegalArgumentException();
        }
        Team teamThatWon = gameService.play(scheduledGame, gameResultDTO.awayTeamGoals(), gameResultDTO.homeTeamGoals());
        return ResponseEntity.ok(new TeamDTO(teamThatWon.getName(), teamThatWon.getPlayerList(), teamThatWon.getRankingPoints()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find all the next games from a specific team")
    public ResponseEntity<List<GameDTO>> findAll(@PathVariable UUID id) {
        Team team = teamService.findById(id);
        if(team == null) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok(gameService.teamsGames(team).stream().map(game -> new GameDTO(game.getDate(), game.getHomeTeam().getName(), game.getAwayTeam().getName())).collect(Collectors.toList()));
    }
}
