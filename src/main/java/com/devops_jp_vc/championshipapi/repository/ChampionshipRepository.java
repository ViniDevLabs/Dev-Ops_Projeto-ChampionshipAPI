package com.devops_jp_vc.championshipapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devops_jp_vc.championshipapi.model.Championship;

import java.util.UUID;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, UUID> {
}
