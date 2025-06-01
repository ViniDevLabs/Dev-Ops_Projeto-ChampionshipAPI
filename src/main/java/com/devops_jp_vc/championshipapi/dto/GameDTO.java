package com.devops_jp_vc.championshipapi.dto;

import java.time.LocalDate;

public record GameDTO(LocalDate date, String homeTeamName, String awayTeamName) {
}
