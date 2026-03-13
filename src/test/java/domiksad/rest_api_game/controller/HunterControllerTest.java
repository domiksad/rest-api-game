package domiksad.rest_api_game.controller;

import domiksad.rest_api_game.dto.HunterDTO;
import domiksad.rest_api_game.service.HunterService;
import domiksad.rest_api_game.service.QuestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HunterController.class)
@ActiveProfiles("test")
public class HunterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HunterService hunterService;

    @MockitoBean
    private QuestService questService;

    @Test
    void createHunterWithValidInformationTest() throws Exception {
        HunterDTO dto = new HunterDTO();
        dto.setId(1L);
        dto.setName("ABC");

        Mockito.when(hunterService.createHunter(Mockito.any()))
                .thenReturn(dto);

        String json = """
                {
                    "name": "ABC"
                }
                """;

        mockMvc.perform(post("/api/hunters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("ABC"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void createHunterEmptyParameterTest() throws Exception {
        String json = """
                {
                    "name": ""
                }
                """;

        mockMvc.perform(post("/api/hunters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").exists());
    }

    @Test
    void createHunterNoParametersTest() throws Exception {
        String json = """
                {
                }
                """;

        mockMvc.perform(post("/api/hunters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllHunters_returnsListOfHunters() throws Exception {
        HunterDTO h1 = new HunterDTO();
        h1.setId(1L);
        h1.setName("Alice");

        HunterDTO h2 = new HunterDTO();
        h2.setId(2L);
        h2.setName("Bob");

        List<HunterDTO> hunters = List.of(h1, h2);

        Mockito.when(hunterService.getAllHunters()).thenReturn(hunters);

        mockMvc.perform(get("/api/hunters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Bob"));
    }
}
