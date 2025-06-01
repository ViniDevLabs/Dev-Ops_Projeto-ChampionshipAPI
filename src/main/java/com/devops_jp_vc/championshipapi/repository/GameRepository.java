package com.devops_jp_vc.championshipapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devops_jp_vc.championshipapi.model.Game;
import com.devops_jp_vc.championshipapi.model.Team;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    @Query("SELECT g FROM Game g WHERE g.homeTeam = :team OR g.awayTeam = :team")
    List<Game> findAllByTeam(Team team);
}
