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

        Team novoTime = new Team();
        novoTime.setName("Flamengo");
        novoTime.setRankingPoints(10);
        novoTime.setPlayerList(new ArrayList<>());
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

        Player jogador = new Player();
        jogador.setId(playerId);
        jogador.setName("João V. César");
        jogador.setPosition("Atacante");
        jogador.setGameCount(0);
        jogador.setHabilityPoints(20);
        jogador.setAge(20);

        Team novoTime = new Team();
        novoTime.setId(teamId);
        novoTime.setName("Flamengo");
        novoTime.setRankingPoints(10);
        novoTime.setPlayerList(new ArrayList<>());

        Team timeAtualizado = new Team();
        timeAtualizado.setId(teamId);
        timeAtualizado.setName("Flamengo");
        timeAtualizado.setRankingPoints(10);
        timeAtualizado.setPlayerList(List.of(jogador));

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
        Player gabigol = new Player();
        gabigol.setName("Gabigol");
        gabigol.setAge(30);
        gabigol.setPosition("Atacante");
        gabigol.setGameCount(0);
        gabigol.setHabilityPoints(100);
        Player neymar = new Player();
        neymar.setName("Neymar");
        neymar.setAge(33);
        neymar.setPosition("Atacante");
        neymar.setGameCount(0);
        neymar.setHabilityPoints(7);
        List<Player> playerList = List.of(gabigol, neymar);
        Team savedTeam = new Team();
        savedTeam.setName("Flamengo");
        savedTeam.setRankingPoints(10);
        savedTeam.setPlayerList(playerList);

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
        Player jogador = new Player();
        jogador.setId(playerId);
        jogador.setName("João V. César");
        jogador.setPosition("Atacante");
        jogador.setGameCount(0);
        jogador.setHabilityPoints(20);
        jogador.setAge(20);

        // Criando time com jogador inicialmente
        Team novoTime = new Team();
        novoTime.setId(teamId);
        novoTime.setName("Flamengo");
        novoTime.setRankingPoints(10);
        novoTime.setPlayerList(new ArrayList<>(List.of(jogador)));

        // Time atualizado após remoção do jogador
        Team timeSemJogador = new Team();
        timeSemJogador.setId(teamId);
        timeSemJogador.setName("Flamengo");
        timeSemJogador.setRankingPoints(10);
        timeSemJogador.setPlayerList(new ArrayList<>());

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
