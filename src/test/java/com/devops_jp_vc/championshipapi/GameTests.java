package com.devops_jp_vc.championshipapi;

import com.devops_jp_vc.championshipapi.controller.GameController;
import com.devops_jp_vc.championshipapi.model.Championship;
import com.devops_jp_vc.championshipapi.model.Game;
import com.devops_jp_vc.championshipapi.model.Team;
import com.devops_jp_vc.championshipapi.service.ChampionshipService;
import com.devops_jp_vc.championshipapi.service.GameService;
import com.devops_jp_vc.championshipapi.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = GameController.class)
class GameTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeamService teamService;
    @MockitoBean
    private GameService gameService;
    @MockitoBean
    private ChampionshipService championshipService;

    private Team teamMock1;
    private Team teamMock2;
    private UUID championshipId;

    @BeforeEach
    void setup() {
        // Criação dos times
        teamMock1 = new Team();
        teamMock1.setId(UUID.randomUUID());
        teamMock1.setName("Flamengo");
        teamMock1.setRankingPoints(10);
        teamMock1.setPlayerList(new ArrayList<>());

        teamMock2 = new Team();
        teamMock2.setId(UUID.randomUUID());
        teamMock2.setName("ABC");
        teamMock2.setRankingPoints(10);
        teamMock2.setPlayerList(new ArrayList<>());

        // Criando um campeonato para ter jogo
        championshipId = UUID.randomUUID();
        Championship championship = new Championship();
        championship.setId(championshipId);
        championship.setName("Copa do Brasil");

        when(teamService.findById(teamMock1.getId())).thenReturn(teamMock1);
        when(teamService.findById(teamMock2.getId())).thenReturn(teamMock2);
        when(championshipService.findById(championshipId)).thenReturn(championship);
    }

    @Test
    void marcarJogo() throws Exception {
        LocalDate gameDate = LocalDate.now();

        Game game = new Game();
        game.setDate(gameDate);
        game.setHomeTeam(teamMock1);
        game.setAwayTeam(teamMock2);

        when(gameService.create(any(Game.class))).thenReturn(game);

        String payload = String.format("""
        {
            "date": "%s",
            "homeTeamId": "%s",
            "awayTeamId": "%s",
            "championshipId": "%s"
        }
        """, gameDate, teamMock1.getId(), teamMock2.getId(), championshipId);

        mockMvc.perform(post("/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.homeTeamName").value("Flamengo"))
                .andExpect(jsonPath("$.awayTeamName").value("ABC"));
    }
}

