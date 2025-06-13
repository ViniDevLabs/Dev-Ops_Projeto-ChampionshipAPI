package com.devops_jp_vc.championshipapi;

import com.devops_jp_vc.championshipapi.controller.PlayerController;
import com.devops_jp_vc.championshipapi.model.Player;
import com.devops_jp_vc.championshipapi.service.PlayerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@AutoConfigureMockMvc
@WebMvcTest(controllers = PlayerController.class)
class PlayerTests {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private PlayerService playerService;

  private Player playerMock1;

  @BeforeEach
  void setup() {
    playerMock1 = Player.builder()
        .name("Zidane")
        .age(26)
        .position("Meio-campista")
        .habilityPoints(95)
        .gameCount(0)
        .build();
  }

  @Test
  void criarJogador() throws Exception {
    String payload = """
        {
          "name": "Zidane",
          "age": 26,
          "position": "meio-campista",
          "habilityPoints": 95,
          "gameCount": 0
        }
                """;

    when(playerService.create(any(Player.class))).thenReturn(playerMock1);

    mockMvc.perform(post("/player")
        .contentType(MediaType.APPLICATION_JSON)
        .content(payload))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Zidane"));
  }

  @Test
  void listarTodosOsJogadores() throws Exception {
    Player playerMock2 = Player.builder()
        .name("Cafu")
        .age(28)
        .position("Lateral")
        .habilityPoints(90)
        .gameCount(0)
        .build();

    when(playerService.findAll()).thenReturn(List.of(playerMock1, playerMock2));

    mockMvc.perform(get("/player"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Zidane"))
        .andExpect(jsonPath("$[1].name").value("Cafu"));
  }

  @Test
  void retornarJogadorPeloId() throws Exception {
    when(playerService.findById(playerMock1.getId())).thenReturn(playerMock1);

    mockMvc.perform(get("/player/{id}", playerMock1.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Zidane"));
  }

}
