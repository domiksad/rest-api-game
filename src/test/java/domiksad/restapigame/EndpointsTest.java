package domiksad.restapigame;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EndpointsTest {

  @Autowired private MockMvc mockMvc;

  @Test
  public void testFilters() throws Exception {
    String questJson1 =
        """
        {
            "name": "Kill BBEG",
            "description": "Kill Big Bad Evil Guy",
            "reward": "Princess",
            "dangerLevel": "HIGH"
        }
        """;

    String questJson2 =
        """
        {
            "name": "Slay goblins",
            "description": "Slay 5 goblins",
            "reward": "Kiss",
            "dangerLevel": "MEDIUM"
        }
        """;

    String questJson3 =
        """
        {
            "name": "Survive return to home",
            "description": "U forgot to pull out the chicken from freezer. Survive",
            "reward": "Life",
            "dangerLevel": "HIGH"
        }
        """;

    UUID quest1Id = createQuest(questJson1);
    UUID quest2Id = createQuest(questJson2);
    UUID quest3Id = createQuest(questJson3);

    mockMvc.perform(post("/api/quests/%s/complete".formatted(quest1Id.toString()))).andExpect(status().isOk());

    mockMvc
        .perform(get("/api/quests?questStatus=FINISHED").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].id").value(hasItem(quest1Id.toString())))
        .andExpect(jsonPath("$[*].id").value(not(hasItem(quest2Id.toString()))))
        .andExpect(jsonPath("$[*].id").value(not(hasItem(quest3Id.toString()))));

    mockMvc
        .perform(get("/api/quests?dangerLevel=HIGH").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].id").value(hasItem(quest1Id.toString())))
        .andExpect(jsonPath("$[*].id").value(hasItem(quest3Id.toString())))
        .andExpect(jsonPath("$[*].id").value(not(hasItem(quest2Id.toString()))));

    mockMvc
        .perform(
            get("/api/quests?dangerLevel=HIGH&questStatus=FINISHED")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].id").value(hasItem(quest1Id.toString())))
        .andExpect(jsonPath("$[*].id").value(not(hasItem(quest2Id.toString()))))
        .andExpect(jsonPath("$[*].id").value(not(hasItem(quest3Id.toString()))));

    mockMvc
        .perform(get("/api/quests").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].id").value(hasItem(quest1Id.toString())))
        .andExpect(jsonPath("$[*].id").value(hasItem(quest2Id.toString())))
        .andExpect(jsonPath("$[*].id").value(hasItem(quest3Id.toString())));
  }

  @Test
  void invalidParameter_throwsException() throws Exception {
    mockMvc
        .perform(
            get("/api/quests?dangerLevel=EXTREME")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    mockMvc
        .perform(
            get("/api/quests?questStatus=OMNIOMMNION")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  private UUID createQuest(String json) throws Exception {
    MvcResult result =
        mockMvc
            .perform(post("/api/quests").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated())
            .andReturn();

    String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
    return UUID.fromString(id);
  }
}
