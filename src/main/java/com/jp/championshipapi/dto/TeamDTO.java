package com.jp.championshipapi.dto;

import com.jp.championshipapi.model.Player;

import java.util.List;

public record TeamDTO(String name, List<Player> playerList, int rankingPoints) {
}
