package com.devops_jp_vc.championshipapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devops_jp_vc.championshipapi.dto.*;
import com.devops_jp_vc.championshipapi.model.Game;
import com.devops_jp_vc.championshipapi.model.Team;
import com.devops_jp_vc.championshipapi.service.ChampionshipService;
import com.devops_jp_vc.championshipapi.service.GameService;
import com.devops_jp_vc.championshipapi.service.TeamService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final TeamService teamService;
    private final ChampionshipService championshipService;

    @PostMapping
    @Operation(summary = "Schedule a game", description = "Schedule a game between two teams on a specific date")
    public ResponseEntity<GameDTO> scheduleGame(@RequestBody GameToScheduleDTO gameToScheduleDTO) {
        Game newGame = new Game();
        newGame.setDate(gameToScheduleDTO.date());
        Team homeTeam = teamService.findById(gameToScheduleDTO.homeTeamId());
        Team awayTeam = teamService.findById(gameToScheduleDTO.awayTeamId());
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("One or more teams don't exist");
        }
        newGame.setHomeTeam(homeTeam);
        newGame.setAwayTeam(awayTeam);
        if (gameToScheduleDTO.championshipId() != null) {
            newGame.setChampionship(championshipService.findById(gameToScheduleDTO.championshipId()));
        }
        Game createdGame = gameService.create(newGame);
        GameDTO gameDTO = new GameDTO(createdGame.getDate(), createdGame.getHomeTeam().getName(),
                createdGame.getAwayTeam().getName());
        return ResponseEntity.ok(gameDTO);
    }

    @PostMapping("/play/{id}")
    @Operation(summary = "Play an already scheduled game", description = "Give the result")
    public ResponseEntity<TeamDTO> play(@PathVariable UUID id, @RequestBody GameResultDTO gameResultDTO) {
        Game scheduledGame = gameService.findById(id);
        if (scheduledGame == null) {
            throw new IllegalArgumentException("This game hasn't been scheduled");
        }
        Team teamThatWon = gameService.play(scheduledGame, gameResultDTO.awayTeamGoals(),
                gameResultDTO.homeTeamGoals());
        return ResponseEntity
                .ok(new TeamDTO(teamThatWon.getName(), teamThatWon.getPlayerList(), teamThatWon.getRankingPoints()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find all the next games from a specific team")
    public ResponseEntity<List<GameDTO>> findAll(@PathVariable UUID id) {
        Team team = teamService.findById(id);
        if (team == null) {
            throw new IllegalArgumentException("Team not found");
        }
        return ResponseEntity.ok(gameService.teamsGames(team).stream()
                .map(game -> new GameDTO(game.getDate(), game.getHomeTeam().getName(), game.getAwayTeam().getName()))
                .toList());
    }
}
