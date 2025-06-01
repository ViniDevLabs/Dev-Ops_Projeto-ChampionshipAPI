package com.devops_jp_vc.championshipapi.dto;

import java.util.List;

import com.devops_jp_vc.championshipapi.model.Player;

public record TeamDTO(String name, List<Player> playerList, int rankingPoints) {
}
