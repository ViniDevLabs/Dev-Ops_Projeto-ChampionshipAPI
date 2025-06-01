package com.devops_jp_vc.championshipapi.dto;

import java.time.LocalDate;
import java.util.UUID;

public record GameToScheduleDTO(LocalDate date, UUID homeTeamId, UUID awayTeamId, UUID championshipId) {
}
