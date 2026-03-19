package domiksad.restapigame.objects;

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
import java.util.UUID;
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
    HunterEntity h1 = new HunterEntity(UUID.randomUUID(), "Alice", new HashSet<>());
    HunterEntity h2 = new HunterEntity(UUID.randomUUID(), "Bob", new HashSet<>());
    HashSet<HunterEntity> hunters = new HashSet<>();
    hunters.add(h1);
    hunters.add(h2);
    return hunters;
  }

  private HashSet<Hunter> getHunterDomainSet() {
    Hunter h1 = new Hunter(UUID.randomUUID(), "Alice", new HashSet<>());
    Hunter h2 = new Hunter(UUID.randomUUID(), "Bob", new HashSet<>());
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

    assertEquals(q1.name(), q2.getName());
    assertEquals(q1.description(), q2.getDescription());
    assertEquals(q1.reward(), q2.getReward());
    assertEquals(q1.dangerLevel(), q2.getDangerLevel());
    assertEquals(QuestStatus.CREATED, q2.getQuestStatus());
  }

  @Test
  void entityToDomain() {
    QuestEntity q1 =
        new QuestEntity(
            UUID.randomUUID(),
            "Quest",
            "Test quest",
            "Happy developer",
            DangerLevel.LOW,
            QuestStatus.FINISHED,
            getHunterEntitySet());
    Quest q2 = mapper.map(q1, Quest.class);

    assertEquals(q1.getId(), q2.getId());
    assertEquals(q1.getName(), q2.getName());
    assertEquals(q1.getDescription(), q2.getDescription());
    assertEquals(q1.getReward(), q2.getReward());
    assertEquals(q1.getDangerLevel(), q2.getDangerLevel());
    assertEquals(q1.getQuestStatus(), q2.getQuestStatus());
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
            UUID.randomUUID(),
            "Quest",
            "Test quest",
            "Happy developer",
            DangerLevel.LOW,
            QuestStatus.FINISHED,
            new HashSet<>());
    QuestEntity q2 = mapper.map(q1, QuestEntity.class);

    assertEquals(q1.getId(), q2.getId());
    assertEquals(q1.getName(), q2.getName());
    assertEquals(q1.getDescription(), q2.getDescription());
    assertEquals(q1.getReward(), q2.getReward());
    assertEquals(q1.getDangerLevel(), q2.getDangerLevel());
    assertEquals(q1.getQuestStatus(), q2.getQuestStatus());
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
            UUID.randomUUID(),
            "Quest",
            "Test quest",
            "Happy developer",
            DangerLevel.LOW,
            QuestStatus.FINISHED,
            new HashSet<>());
    QuestResponseDto q2 = mapper.map(q1, QuestResponseDto.class);

    assertEquals(q1.getId(), q2.id());
    assertEquals(q1.getName(), q2.name());
    assertEquals(q1.getDescription(), q2.description());
    assertEquals(q1.getReward(), q2.reward());
    assertEquals(q1.getDangerLevel(), q2.dangerLevel());
    assertEquals(q1.getQuestStatus(), q2.questStatus());
  }

  @Test
  void testBiDirectionalMapping_noInfiniteLoop() {
    Quest quest =
        new Quest(
            UUID.randomUUID(), "Quest", "Desc", "Reward", DangerLevel.LOW, QuestStatus.CREATED, new HashSet<>());
    Hunter hunter = new Hunter(UUID.randomUUID(), "Alice", new HashSet<>());
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
