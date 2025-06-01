package com.devops_jp_vc.championshipapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devops_jp_vc.championshipapi.dto.PlayerDTO;
import com.devops_jp_vc.championshipapi.model.Player;
import com.devops_jp_vc.championshipapi.service.PlayerService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping
    @Operation(summary = "Create a player")
    public ResponseEntity<PlayerDTO> create(@RequestBody Player player) {
        Player newPlayer = playerService.create(player);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPlayer.getId())
                .toUri();
        PlayerDTO playerDTO = new PlayerDTO(newPlayer.getName(), newPlayer.getAge(), newPlayer.getHabilityPoints(),
                newPlayer.getPosition());
        return ResponseEntity.created(location).body(playerDTO);
    }

    @GetMapping
    @Operation(summary = "Get all the existing players")
    public ResponseEntity<List<PlayerDTO>> findAll() {
        List<Player> playerList = playerService.findAll();
        List<PlayerDTO> returnList = playerList.stream()
                .map(p -> new PlayerDTO(p.getName(), p.getAge(), p.getHabilityPoints(), p.getPosition()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(returnList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a player from its id")
    public ResponseEntity<PlayerDTO> findOne(@PathVariable UUID id) {
        Player player = playerService.findById(id);
        PlayerDTO playerDTO = new PlayerDTO(player.getName(), player.getAge(), player.getHabilityPoints(),
                player.getPosition());
        return ResponseEntity.ok(playerDTO);
    }
}
