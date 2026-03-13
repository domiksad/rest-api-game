package domiksad.rest_api_game;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFilters() throws Exception {
        String questJson1 = """
                {
                    "name": "Kill BBEG ",
                    "description": "Kill Big Bad Evil Guy",
                    "reward": "Princess",
                    "dangerLevel": "HIGH"
                }
                """;

        String questJson2 = """
                {
                    "name": "Slay goblins",
                    "description": "Slay 5 goblins",
                    "reward": "Kiss",
                    "dangerLevel": "MEDIUM"
                }
                """;

        String questJson3 = """
                {
                    "name": "Survive return to home",
                    "description": "U forgot to pull out the chicken from freezer. Survive",
                    "reward": "Life",
                    "dangerLevel": "HIGH"
                }
                """;

        Long quest1Id = createQuest(questJson1);
        Long quest2Id = createQuest(questJson2);
        Long quest3Id = createQuest(questJson3);

        mockMvc.perform(post("/api/quests/%d/complete".formatted(quest1Id)))
                .andExpect(status().isOk());


        mockMvc.perform(get("/api/quests?questStatus=COMPLETED")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(hasItem(quest1Id.intValue())))
                .andExpect(jsonPath("$[*].id").value(not(hasItem(quest2Id.intValue()))))
                .andExpect(jsonPath("$[*].id").value(not(hasItem(quest3Id.intValue()))));

        mockMvc.perform(get("/api/quests?dangerLevel=HIGH")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(hasItem(quest1Id.intValue())))
                .andExpect(jsonPath("$[*].id").value(hasItem(quest3Id.intValue())))
                .andExpect(jsonPath("$[*].id").value(not(hasItem(quest2Id.intValue()))));

        mockMvc.perform(get("/api/quests?dangerLevel=HIGH&questStatus=COMPLETED")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(hasItem(quest1Id.intValue())))
                .andExpect(jsonPath("$[*].id").value(not(hasItem(quest2Id.intValue()))))
                .andExpect(jsonPath("$[*].id").value(not(hasItem(quest3Id.intValue()))));

        mockMvc.perform(get("/api/quests")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(hasItem(quest1Id.intValue())))
                .andExpect(jsonPath("$[*].id").value(hasItem(quest2Id.intValue())))
                .andExpect(jsonPath("$[*].id").value(hasItem(quest3Id.intValue())));
    }

    private Long createQuest(String json) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/quests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        Number idNumber = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        return idNumber.longValue();
    }
}
