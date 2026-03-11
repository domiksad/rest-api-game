package domiksad.rest_api_game;

import domiksad.rest_api_game.dto.HunterDTO;
import domiksad.rest_api_game.dto.QuestDTO;
import domiksad.rest_api_game.entity.Hunter;
import domiksad.rest_api_game.entity.Quest;
import domiksad.rest_api_game.enums.DangerLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTest {
    private ModelMapper mapper;

    @BeforeEach
    public void setup() {
        this.mapper = new ModelMapper();
    }

    @Test
    public void testHunterMappingToHunterDTO(){
        Hunter hunter = new Hunter("abc");

        HunterDTO hunterDTO = this.mapper.map(hunter, HunterDTO.class);

        assertEquals(hunter.getId(), hunterDTO.getId());
        assertEquals(hunter.getName(), hunterDTO.getName());
    }

    @Test
    public void testHunterDTOMappingToHunter(){
        HunterDTO hunterDTO = new HunterDTO("abc");

        Hunter hunter = this.mapper.map(hunterDTO, Hunter.class);

        assertEquals(hunterDTO.getId(), hunter.getId());
        assertEquals(hunterDTO.getName(), hunter.getName());
    }

    @Test
    public void testQuestMappingToQuestDTO(){
        Quest quest = new Quest("abc", "def", "ghi", DangerLevel.LOW);

        QuestDTO questDTO = this.mapper.map(quest, QuestDTO.class);

        assertEquals(quest.getId(), questDTO.getId());
        assertEquals(quest.getName(), questDTO.getName());
        assertEquals(quest.getDescription(), questDTO.getDescription());
        assertEquals(quest.getReward(), questDTO.getReward());
        assertEquals(quest.getDangerLevel(), questDTO.getDangerLevel());
        assertEquals(quest.getQuestStatus(), questDTO.getQuestStatus());
    }

    @Test
    public void testQuestDTOMappingToQuest(){
        QuestDTO questDTO = new QuestDTO("abc", "def", "ghi", DangerLevel.LOW);

        Quest quest = this.mapper.map(questDTO, Quest.class);

        assertEquals(questDTO.getId(), quest.getId());
        assertEquals(questDTO.getName(), quest.getName());
        assertEquals(questDTO.getDescription(), quest.getDescription());
        assertEquals(questDTO.getReward(), quest.getReward());
        assertEquals(questDTO.getDangerLevel(), quest.getDangerLevel());
        assertEquals(questDTO.getQuestStatus(), quest.getQuestStatus());
    }
}
