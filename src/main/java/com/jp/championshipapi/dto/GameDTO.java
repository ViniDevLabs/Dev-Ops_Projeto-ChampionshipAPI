package com.jp.championshipapi.dto;

import java.time.LocalDate;

public record GameDTO (LocalDate date, String homeTeamName, String awayTeamName){
}
