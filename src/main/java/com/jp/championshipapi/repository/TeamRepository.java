package com.jp.championshipapi.repository;

import com.jp.championshipapi.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
}
