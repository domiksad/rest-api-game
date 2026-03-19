package domiksad.restapigame;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import domiksad.restapigame.application.service.HunterService;
import domiksad.restapigame.application.service.QuestService;
import domiksad.restapigame.presentation.controller.HunterController;
import domiksad.restapigame.presentation.dto.HunterResponseDto;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HunterController.class)
@ActiveProfiles("test")
public class HunterControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private HunterService hunterService;

  @MockitoBean private QuestService questService;

  private UUID id;

  @BeforeEach
  void setup() {
    id = UUID.randomUUID();
  }

  @Test
  void createHunterWithValidInformationTest() throws Exception {
    String name = "abc";

    HunterResponseDto dto = new HunterResponseDto(id, name);

    Mockito.when(hunterService.createHunter(Mockito.any())).thenReturn(dto);

    String json =
        String.format(
            """
            {
                "name": "%s"
            }
            """,
            name);

    mockMvc
        .perform(
            post("/api/hunters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value(name))
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  void createHunterEmptyParameterTest() throws Exception {
    String json =
        """
        {
            "name": ""
        }
        """;

    mockMvc
        .perform(
            post("/api/hunters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.name").exists());
  }

  @Test
  void createHunterNoParametersTest() throws Exception {
    String json =
        """
        {
        }
        """;

    mockMvc
        .perform(
            post("/api/hunters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getAllHunters_returnsListOfHunters() throws Exception {
    UUID id1 = UUID.randomUUID();
    UUID id2 = UUID.randomUUID();
    String name1 = "Alice";
    String name2 = "Bob";

    HunterResponseDto h1 = new HunterResponseDto(id1, name1);
    HunterResponseDto h2 = new HunterResponseDto(id2, name2);

    List<HunterResponseDto> hunters = List.of(h1, h2);

    Mockito.when(hunterService.getAllHunters()).thenReturn(hunters);

    mockMvc
        .perform(get("/api/hunters"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id1.toString()))
        .andExpect(jsonPath("$[0].name").value(name1))
        .andExpect(jsonPath("$[1].id").value(id2.toString()))
        .andExpect(jsonPath("$[1].name").value(name2));
  }
}
