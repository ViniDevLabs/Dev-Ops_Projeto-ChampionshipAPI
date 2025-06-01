package com.devops_jp_vc.championshipapi.service;

import java.util.List;
import java.util.UUID;

import com.devops_jp_vc.championshipapi.dto.PlayerDTO;
import com.devops_jp_vc.championshipapi.model.Player;

public interface PlayerService {
    Player findById(UUID id);

    List<Player> findAll();

    Player create(Player player);
}
