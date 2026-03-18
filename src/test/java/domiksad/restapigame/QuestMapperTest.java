package domiksad.restapigame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domiksad.restapigame.application.mapper.MapperConfig;
import domiksad.restapigame.domain.hunter.Hunter;
import domiksad.restapigame.domain.quest.DangerLevel;
import domiksad.restapigame.domain.quest.Quest;
import domiksad.restapigame.domain.quest.QuestStatus;
import domiksad.restapigame.infrastructure.entity.HunterEntity;
import domiksad.restapigame.infrastructure.entity.QuestEntity;
import domiksad.restapigame.presentation.dto.QuestRequestDto;
import domiksad.restapigame.presentation.dto.QuestResponseDto;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class QuestMapperTest {

  private ModelMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new MapperConfig().modelMapper();
  }

  private HashSet<HunterEntity> getHunterEntitySet() {
    HunterEntity h1 = new HunterEntity(1L, "Alice", new HashSet<>());
    HunterEntity h2 = new HunterEntity(2L, "Bob", new HashSet<>());
    HashSet<HunterEntity> hunters = new HashSet<>();
    hunters.add(h1);
    hunters.add(h2);
    return hunters;
  }

  private HashSet<Hunter> getHunterDomainSet() {
    Hunter h1 = new Hunter(1L, "Alice", new HashSet<>());
    Hunter h2 = new Hunter(2L, "Bob", new HashSet<>());
    HashSet<Hunter> hunters = new HashSet<>();
    hunters.add(h1);
    hunters.add(h2);
    return hunters;
  }

  // requestDto -> Entity -> Domain -> Entity -> responseDto

  @Test
  void requestDtoToEntity() {
    QuestRequestDto q1 =
        new QuestRequestDto("Quest", "Test quest", "Happy developer", DangerLevel.LOW);
    QuestEntity q2 = mapper.map(q1, QuestEntity.class);

    assertEquals("Quest", q2.getName());
    assertEquals("Test quest", q2.getDescription());
    assertEquals("Happy developer", q2.getReward());
    assertEquals(DangerLevel.LOW, q2.getDangerLevel());
    assertEquals(QuestStatus.CREATED, q2.getQuestStatus());
  }

  @Test
  void entityToDomain() {
    QuestEntity q1 =
        new QuestEntity(
            1L,
            "Quest",
            "Test quest",
            "Happy developer",
            DangerLevel.LOW,
            QuestStatus.COMPLETED,
            getHunterEntitySet());
    Quest q2 = mapper.map(q1, Quest.class);

    assertEquals(1L, q2.getId());
    assertEquals("Quest", q2.getName());
    assertEquals("Test quest", q2.getDescription());
    assertEquals("Happy developer", q2.getReward());
    assertEquals(DangerLevel.LOW, q2.getDangerLevel());
    assertEquals(QuestStatus.COMPLETED, q2.getQuestStatus());
    q1.getAssignedHunters()
        .forEach(
            he ->
                assertTrue(
                    q2.getAssignedHunters().stream()
                        .anyMatch(
                            h ->
                                h.getId().equals(he.getId()) && h.getName().equals(he.getName()))));
  }

  @Test
  void domainToEntity() {
    Quest q1 =
        new Quest(
            1L,
            "Quest",
            "Test quest",
            "Happy developer",
            DangerLevel.LOW,
            QuestStatus.COMPLETED,
            new HashSet<>());
    QuestEntity q2 = mapper.map(q1, QuestEntity.class);

    assertEquals(1L, q2.getId());
    assertEquals("Quest", q2.getName());
    assertEquals("Test quest", q2.getDescription());
    assertEquals("Happy developer", q2.getReward());
    assertEquals(DangerLevel.LOW, q2.getDangerLevel());
    assertEquals(QuestStatus.COMPLETED, q2.getQuestStatus());
    q1.getAssignedHunters()
        .forEach(
            h ->
                assertTrue(
                    q2.getAssignedHunters().stream()
                        .anyMatch(
                            he ->
                                he.getId().equals(h.getId()) && he.getName().equals(h.getName()))));
  }

  @Test
  void entityToResponseDto() {
    QuestEntity q1 =
        new QuestEntity(
            1L,
            "Quest",
            "Test quest",
            "Happy developer",
            DangerLevel.LOW,
            QuestStatus.COMPLETED,
            new HashSet<>());
    QuestResponseDto q2 = mapper.map(q1, QuestResponseDto.class);

    assertEquals(1L, q2.id());
    assertEquals("Quest", q2.name());
    assertEquals("Test quest", q2.description());
    assertEquals("Happy developer", q2.reward());
    assertEquals(DangerLevel.LOW, q2.dangerLevel());
    assertEquals(QuestStatus.COMPLETED, q2.questStatus());
  }

  @Test
  void testBiDirectionalMapping_noInfiniteLoop() {
    Quest quest =
        new Quest(
            1L, "Quest", "Desc", "Reward", DangerLevel.LOW, QuestStatus.CREATED, new HashSet<>());
    Hunter hunter = new Hunter(1L, "Alice", new HashSet<>());
    quest.getAssignedHunters().add(hunter);
    hunter.getAssignedQuests().add(quest);

    Quest mappedQuest = mapper.map(quest, Quest.class);

    assertEquals(quest.getId(), mappedQuest.getId());
    assertEquals(quest.getName(), mappedQuest.getName());

    assertEquals(1, mappedQuest.getAssignedHunters().size());
    Hunter mappedHunter = mappedQuest.getAssignedHunters().iterator().next();
    assertEquals("Alice", mappedHunter.getName());

    assertNotNull(mappedHunter.getAssignedQuests());
    assertEquals(1, mappedHunter.getAssignedQuests().size());
    assertEquals(mappedQuest.getId(), mappedHunter.getAssignedQuests().iterator().next().getId());
  }
}
