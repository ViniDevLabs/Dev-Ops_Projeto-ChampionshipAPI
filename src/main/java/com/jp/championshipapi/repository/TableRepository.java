package com.jp.championshipapi.repository;

import com.jp.championshipapi.model.Table;
import com.jp.championshipapi.model.TableEntry;
import com.jp.championshipapi.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TableRepository extends JpaRepository<Table, UUID> {
}
