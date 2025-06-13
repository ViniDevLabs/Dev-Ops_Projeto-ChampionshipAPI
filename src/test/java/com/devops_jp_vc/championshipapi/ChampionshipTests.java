package com.devops_jp_vc.championshipapi;

import com.devops_jp_vc.championshipapi.controller.ChampionshipController;
import com.devops_jp_vc.championshipapi.dto.TableEntryDTO;
import com.devops_jp_vc.championshipapi.model.Championship;
import com.devops_jp_vc.championshipapi.model.Table;
import com.devops_jp_vc.championshipapi.model.Team;
import com.devops_jp_vc.championshipapi.service.ChampionshipService;
import com.devops_jp_vc.championshipapi.service.TeamService;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@AutoConfigureMockMvc
@WebMvcTest(controllers = ChampionshipController.class)
class ChampionshipTests {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ChampionshipService championshipService;

  @MockitoBean
  private TeamService teamService;

  private Championship championshipMock;

  @BeforeEach
  void setup() {
    championshipMock = Championship.builder().name("La Liga").build();
  }

  @Test
  void criarCampeonato() throws Exception {
    String payload = """
        {
          "championshipName": "La Liga"
        }
                        """;

    Table tableMock = Table.builder().championship(championshipMock).build();

    when(championshipService.create(any(Championship.class))).thenReturn(tableMock);

    mockMvc.perform(post("/championship")
        .contentType(MediaType.APPLICATION_JSON)
        .content(payload))
        .andExpect(status().isOk())
        .andExpect(content().string("Championship La Liga has been created"));
  }

  @Test
  void retornarCampeonatoPeloId() throws Exception {
    List<TableEntryDTO> tabelaEsperada = List.of(new TableEntryDTO("Barcelona", 10),
        new TableEntryDTO("Real Madrid", 11));

    when(championshipService.findById(championshipMock.getId())).thenReturn(championshipMock);
    when(championshipService.getTable(championshipMock)).thenReturn(tabelaEsperada);

    mockMvc.perform(get("/championship/{id}", championshipMock.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].teamName").value("Barcelona"))
        .andExpect(jsonPath("$[0].points").value(10))
        .andExpect(jsonPath("$[1].teamName").value("Real Madrid"))
        .andExpect(jsonPath("$[1].points").value(11));
  }

  @Test
  void adicionarTimeAoCampeonato_semIdCampeonato() throws Exception {
    String payload = """
        {
          "teamId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        }
                        """;

    mockMvc.perform(post("/championship/addTeam")
        .contentType(MediaType.APPLICATION_JSON)
        .content(payload))
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void adicionarTimeAoCampeonato_comPayloadCompleto() throws Exception {
    Team teamMock = Team.builder()
        .name("Valencia")
        .rankingPoints(10)
        .build();

    String payload = """
        {
          "teamId": "%s",
          "championshipId": "%s"
        }
                        """.formatted(teamMock.getId(), championshipMock.getId());

    TableEntryDTO rowMock = new TableEntryDTO(teamMock.getName(), 0);

    when(teamService.findById(teamMock.getId())).thenReturn(teamMock);
    when(championshipService.findById(championshipMock.getId())).thenReturn(championshipMock);
    when(championshipService.addTeam(teamMock, championshipMock)).thenReturn(rowMock);

    mockMvc.perform(post("/championship/addTeam")
        .contentType(MediaType.APPLICATION_JSON)
        .content(payload))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.teamName").value("Valencia"))
        .andExpect(jsonPath("$.points").value(0));
  }
}
