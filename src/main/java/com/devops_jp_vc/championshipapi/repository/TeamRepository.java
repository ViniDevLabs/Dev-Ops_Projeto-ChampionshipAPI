package com.devops_jp_vc.championshipapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devops_jp_vc.championshipapi.model.Team;

import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
}
