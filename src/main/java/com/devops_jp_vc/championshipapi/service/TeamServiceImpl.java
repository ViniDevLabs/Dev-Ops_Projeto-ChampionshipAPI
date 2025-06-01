package com.devops_jp_vc.championshipapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devops_jp_vc.championshipapi.model.Player;
import com.devops_jp_vc.championshipapi.model.Team;
import com.devops_jp_vc.championshipapi.repository.PlayerRepository;
import com.devops_jp_vc.championshipapi.repository.TeamRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team findById(UUID id) {
        return teamRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Team create(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team addPlayer(Player player, Team team) {
        team.getPlayerList().add(player);
        player.setTeam(team);
        playerRepository.save(player);
        return teamRepository.save(team);
    }

    @Override
    public Team removePlayer(Player player, Team team) {
        if (team.getPlayerList().contains(player)) {
            team.getPlayerList().remove(player);
            teamRepository.save(team);
        }
        return team;
    }
}
