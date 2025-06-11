package com.devops_jp_vc.championshipapi;

import com.devops_jp_vc.championshipapi.controller.TeamController;
import com.devops_jp_vc.championshipapi.model.Player;
import com.devops_jp_vc.championshipapi.model.Team;
import com.devops_jp_vc.championshipapi.service.PlayerService;
import com.devops_jp_vc.championshipapi.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = TeamController.class)
class TeamTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeamService teamService;
    @MockitoBean
    private PlayerService playerService;

    @Test
    void criarTimeVazio() throws Exception {
        String payload = """
                {
                    "name": "Flamengo",
                    "rankingPoints": 10
                }
                """;

        Team novoTime = Team.builder()
                .name("Flamengo")
                .rankingPoints(10)
                .playerList(new ArrayList<>())
                .build();

        // Mocka o comportamento do service
        when(teamService.create(any(Team.class))).thenReturn(novoTime);

        mockMvc.perform(post("/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Flamengo"));
    }

    @Test
    void adicionarJogadorAoTime() throws Exception {
        UUID playerId = UUID.randomUUID();
        UUID teamId = UUID.randomUUID();

        Player jogador = Player.builder()
                .id(playerId)
                .name("João V. César")
                .position("Atacante")
                .gameCount(0)
                .habilityPoints(20)
                .age(20)
                .build();

        Team novoTime = Team.builder()
                .id(teamId)
                .name("Flamengo")
                .rankingPoints(10)
                .playerList(new ArrayList<>())
                .build();

        Team timeAtualizado = Team.builder()
                .id(teamId)
                .name("Flamengo")
                .rankingPoints(10)
                .playerList(List.of(jogador))
                .build();

        // Mocka o retorno dos services
        when(playerService.findById(playerId)).thenReturn(jogador);
        when(teamService.findById(teamId)).thenReturn(novoTime);
        when(teamService.addPlayer(jogador, novoTime)).thenReturn(timeAtualizado);

        mockMvc.perform(post("/team/addPlayer")
                .param("playerId", playerId.toString())
                .param("teamId", teamId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerList[0].name").value("João V. César"));
    }

    @Test
    void criarTimeComJogadores() throws Exception {
        String payload = """
                {
                    "name": "Flamengo",
                    "playerList": [
                        {"name": "Gabigol", "age": 30, "position": "Atacante", "gameCount": 0, "habilityPoints": 100},
                        {"name": "Neymar", "age": 33, "position": "Atacante", "gameCount": 0, "habilityPoints": 7}
                    ],
                    "rankingPoints": 10
                }
                """;

        // Simula o que o mock deve retornar
        Player gabigol = Player.builder()
                .name("Gabigol")
                .position("Atacante")
                .gameCount(0)
                .habilityPoints(100)
                .age(30)
                .build();

        Player neymar = Player.builder()
                .name("Neymar")
                .position("Atacante")
                .gameCount(0)
                .habilityPoints(7)
                .age(33)
                .build();

        List<Player> playerList = List.of(gabigol, neymar);
        Team savedTeam = Team.builder()
                .name("Flamengo")
                .rankingPoints(10)
                .playerList(playerList)
                .build();

        when(teamService.create(any(Team.class))).thenReturn(savedTeam);
        mockMvc.perform(post("/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Flamengo"))
                .andExpect(jsonPath("$.playerList[0].name").value("Gabigol"))
                .andExpect(jsonPath("$.playerList[1].name").value("Neymar"));
    }

    @Test
    void tirarJogadorDoTime() throws Exception {
        UUID playerId = UUID.randomUUID();
        UUID teamId = UUID.randomUUID();

        // Criando jogador
        Player jogador = Player.builder()
                .id(playerId)
                .name("João V. César")
                .position("Atacante")
                .gameCount(0)
                .habilityPoints(20)
                .age(20)
                .build();

        // Criando time com jogador inicialmente

        Team novoTime = Team.builder()
                .id(teamId)
                .name("Flamengo")
                .rankingPoints(10)
                .playerList(new ArrayList<>(List.of(jogador)))
                .build();

        // Time atualizado após remoção do jogador
        Team timeSemJogador = Team.builder()
                .id(teamId)
                .name("Flamengo")
                .rankingPoints(10)
                .playerList(new ArrayList<>())
                .build();

        when(playerService.findById(playerId)).thenReturn(jogador);
        when(teamService.findById(teamId)).thenReturn(novoTime);
        when(teamService.removePlayer(jogador, novoTime)).thenReturn(timeSemJogador);

        mockMvc.perform(delete("/team/removePlayer")
                .param("playerId", playerId.toString())
                .param("teamId", teamId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerList").isEmpty());
    }

}
