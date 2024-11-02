package com.jp.championshipapi.controller;

import com.jp.championshipapi.dto.TeamDTO;
import com.jp.championshipapi.model.Player;
import com.jp.championshipapi.model.Team;
import com.jp.championshipapi.service.PlayerService;
import com.jp.championshipapi.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;

    @PostMapping
    @Operation(summary = "Create a new team")
    public ResponseEntity<TeamDTO> create(@RequestBody Team team) {
        Team newTeam = teamService.create(team);
        TeamDTO teamDTO = new TeamDTO(newTeam.getName(), newTeam.getPlayerList(), newTeam.getRankingPoints());
        return ResponseEntity.ok(teamDTO);
    }

    @GetMapping
    @Operation(summary = "Find all teams")
    public ResponseEntity<List<TeamDTO>> findAll() {
        List<Team> teams = teamService.findAll();
        List<TeamDTO> teamDTOList = teams.stream()
                .map(t -> new TeamDTO(t.getName(), t.getPlayerList(), t.getRankingPoints()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find team by ID")
    public ResponseEntity<TeamDTO> findOne(@PathVariable UUID id) {
        Team team = teamService.findById(id);
        return ResponseEntity.ok(new TeamDTO(team.getName(), team.getPlayerList(), team.getRankingPoints()));
    }

    @PostMapping("/addPlayer")
    @Operation(summary = "Add a player to a team", description = "Add an already existing player to your team")
    public ResponseEntity<TeamDTO> addPlayer(@RequestParam UUID playerId, @RequestParam UUID teamId) {
        Player player = playerService.findById(playerId);
        Team team = teamService.findById(teamId);

        if (team == null || player == null) {
            throw new IllegalArgumentException("Invalid team or player ID.");
        }

        Team updatedTeam = teamService.addPlayer(player, team);
        TeamDTO teamDTO = new TeamDTO(updatedTeam.getName(), updatedTeam.getPlayerList(), updatedTeam.getRankingPoints());

        return ResponseEntity.ok(teamDTO);
    }

    @DeleteMapping("/removePlayer")
    @Operation(summary = "Remove a player from the team")
    public ResponseEntity<TeamDTO> removePlayer(@RequestParam UUID playerid, @RequestParam UUID teamId) {
        Player player = playerService.findById(playerid);
        Team team = teamService.findById(teamId);
        if (team == null || player == null) {
            throw new IllegalArgumentException("Invalid team or player ID.");
        }
        Team updatedTeam = teamService.removePlayer(player, team);
        TeamDTO teamDTO = new TeamDTO(updatedTeam.getName(), updatedTeam.getPlayerList(), updatedTeam.getRankingPoints());

        return ResponseEntity.ok(teamDTO);
    }
}
