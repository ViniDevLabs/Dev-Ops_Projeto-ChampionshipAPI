package com.jp.championshipapi.repository;

import com.jp.championshipapi.model.Game;
import com.jp.championshipapi.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    @Query("SELECT g FROM Game g WHERE g.homeTeam = :team OR g.awayTeam = :team")
    List<Game> findAllByTeam(Team team);
}
