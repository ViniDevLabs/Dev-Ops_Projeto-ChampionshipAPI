package com.jp.championshipapi.service;

import com.jp.championshipapi.dto.PlayerDTO;
import com.jp.championshipapi.model.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerService {
    Player findById(UUID id);
    List<Player> findAll();
    Player create(Player player);
}
