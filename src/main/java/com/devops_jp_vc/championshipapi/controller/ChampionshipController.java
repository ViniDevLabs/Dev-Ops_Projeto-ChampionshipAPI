package com.devops_jp_vc.championshipapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devops_jp_vc.championshipapi.dto.ChampionshipDTO;
import com.devops_jp_vc.championshipapi.dto.ChampionshipTeamAddDTO;
import com.devops_jp_vc.championshipapi.dto.TableEntryDTO;
import com.devops_jp_vc.championshipapi.model.Championship;
import com.devops_jp_vc.championshipapi.model.Team;
import com.devops_jp_vc.championshipapi.service.ChampionshipService;
import com.devops_jp_vc.championshipapi.service.TeamService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/championship")
public class ChampionshipController {
    @Autowired
    private ChampionshipService championshipService;
    @Autowired
    private TeamService teamService;

    @PostMapping
    @Operation(summary = "Create a Championship")
    public ResponseEntity<String> create(@RequestBody ChampionshipDTO championshipDTO) {
        Championship championship = new Championship();
        championship.setName(championshipDTO.championshipName());
        championshipService.create(championship);
        return ResponseEntity.ok().body("Championship " + championship.getName() + "has been created");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get the full championship table")
    public ResponseEntity<List<TableEntryDTO>> getAll(@PathVariable UUID id) {
        Championship championship = championshipService.findById(id);
        return ResponseEntity.ok(championshipService.getTable(championship));
    }

    @PostMapping("/addTeam")
    @Operation(summary = "Add a team to the championship")
    public ResponseEntity<TableEntryDTO> addTeam(@RequestBody ChampionshipTeamAddDTO ctaDTO) {
        if (ctaDTO.championshipId() == null) {
            throw new IllegalArgumentException("Championship not found");
        }
        Team team = teamService.findById(ctaDTO.teamId());
        Championship championship = championshipService.findById(ctaDTO.championshipId());
        TableEntryDTO row = championshipService.addTeam(team, championship);
        return ResponseEntity.ok(row);
    }
}
