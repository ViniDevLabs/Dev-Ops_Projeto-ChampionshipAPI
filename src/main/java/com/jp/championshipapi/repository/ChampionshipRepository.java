package com.jp.championshipapi.repository;

import com.jp.championshipapi.model.Championship;
import com.jp.championshipapi.model.Table;
import com.jp.championshipapi.model.TableEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, UUID> {
}
