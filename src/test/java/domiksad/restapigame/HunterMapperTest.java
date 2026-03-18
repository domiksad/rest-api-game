package domiksad.restapigame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domiksad.restapigame.application.mapper.MapperConfig;
import domiksad.restapigame.domain.hunter.Hunter;
import domiksad.restapigame.domain.quest.DangerLevel;
import domiksad.restapigame.domain.quest.Quest;
import domiksad.restapigame.domain.quest.QuestStatus;
import domiksad.restapigame.infrastructure.entity.HunterEntity;
import domiksad.restapigame.infrastructure.entity.QuestEntity;
import domiksad.restapigame.presentation.dto.HunterRequestDto;
import domiksad.restapigame.presentation.dto.HunterResponseDto;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class HunterMapperTest {

  private ModelMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new MapperConfig().modelMapper();
  }

  private Set<QuestEntity> getQuestEntitySet() {
    QuestEntity q1 = new QuestEntity(1L, "Quest1", "Desc1", "Reward1", DangerLevel.LOW, QuestStatus.CREATED, new HashSet<>());
    QuestEntity q2 = new QuestEntity(2L, "Quest2", "Desc2", "Reward2", DangerLevel.MEDIUM, QuestStatus.COMPLETED, new HashSet<>());
    Set<QuestEntity> quests = new HashSet<>();
    quests.add(q1);
    quests.add(q2);
    return quests;
  }

  private Set<Quest> getQuestDomainSet() {
    Quest q1 = new Quest(1L, "Quest1", "Desc1", "Reward1", DangerLevel.LOW, QuestStatus.CREATED, new HashSet<>());
    Quest q2 = new Quest(2L, "Quest2", "Desc2", "Reward2", DangerLevel.MEDIUM, QuestStatus.COMPLETED, new HashSet<>());
    Set<Quest> quests = new HashSet<>();
    quests.add(q1);
    quests.add(q2);
    return quests;
  }

  // requestDto -> Entity -> Domain -> Entity -> responseDto

  @Test
  void requestDtoToEntity() {
    HunterRequestDto h1 = new HunterRequestDto("John");
    HunterEntity h2 = mapper.map(h1, HunterEntity.class);

    assertEquals("John", h2.getName());
  }

  @Test
  void entityToDomain() {
    HunterEntity h1 = new HunterEntity(1L, "John", getQuestEntitySet());
    Hunter h2 = mapper.map(h1, Hunter.class);

    assertEquals(1L, h2.getId());
    assertEquals("John", h2.getName());

    h1.getAssignedQuests().forEach(qe ->
        assertTrue(h2.getAssignedQuests().stream()
            .anyMatch(q -> q.getId().equals(qe.getId()) && q.getName().equals(qe.getName())))
    );
  }

  @Test
  void domainToEntity() {
    Hunter h1 = new Hunter(1L, "John", getQuestDomainSet());
    HunterEntity h2 = mapper.map(h1, HunterEntity.class);

    assertEquals(1L, h2.getId());
    assertEquals("John", h2.getName());

    h1.getAssignedQuests().forEach(q ->
        assertTrue(h2.getAssignedQuests().stream()
            .anyMatch(qe -> qe.getId().equals(q.getId()) && qe.getName().equals(q.getName())))
    );
  }

  @Test
  void entityToResponseDto() {
    HunterEntity h1 = new HunterEntity(1L, "John", new HashSet<>());
    HunterResponseDto h2 = mapper.map(h1, HunterResponseDto.class);

    assertEquals(1L, h2.id());
    assertEquals("John", h2.name());
  }

  @Test
  void testBiDirectionalMapping_noInfiniteLoop() {
    Hunter hunter = new Hunter(1L, "John", new HashSet<>());
    Quest quest = new Quest(1L, "Quest1", "Desc", "Reward", DangerLevel.LOW, QuestStatus.CREATED, new HashSet<>());
    hunter.getAssignedQuests().add(quest);
    quest.getAssignedHunters().add(hunter);

    Hunter mappedHunter = mapper.map(hunter, Hunter.class);

    assertEquals(hunter.getId(), mappedHunter.getId());
    assertEquals(hunter.getName(), mappedHunter.getName());

    assertEquals(1, mappedHunter.getAssignedQuests().size());
    Quest mappedQuest = mappedHunter.getAssignedQuests().iterator().next();
    assertEquals(quest.getId(), mappedQuest.getId());
    assertEquals(quest.getName(), mappedQuest.getName());

    assertEquals(1, mappedQuest.getAssignedHunters().size());
    Hunter mappedBackHunter = mappedQuest.getAssignedHunters().iterator().next();
    assertEquals(mappedHunter.getId(), mappedBackHunter.getId());
    assertEquals(mappedHunter.getName(), mappedBackHunter.getName());
  }
}
