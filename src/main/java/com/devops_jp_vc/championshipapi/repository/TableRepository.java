package com.devops_jp_vc.championshipapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devops_jp_vc.championshipapi.model.Table;
import com.devops_jp_vc.championshipapi.model.TableEntry;
import com.devops_jp_vc.championshipapi.model.Team;

import java.util.UUID;

public interface TableRepository extends JpaRepository<Table, UUID> {
}
