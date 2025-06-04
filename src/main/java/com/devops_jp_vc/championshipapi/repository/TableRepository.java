package com.devops_jp_vc.championshipapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devops_jp_vc.championshipapi.model.Table;

import java.util.UUID;

public interface TableRepository extends JpaRepository<Table, UUID> {
}
