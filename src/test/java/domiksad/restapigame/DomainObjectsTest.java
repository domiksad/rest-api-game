package domiksad.restapigame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domiksad.restapigame.domain.exception.WorldLogicException;
import domiksad.restapigame.domain.hunter.Hunter;
import domiksad.restapigame.domain.quest.DangerLevel;
import domiksad.restapigame.domain.quest.Quest;
import domiksad.restapigame.domain.quest.QuestStatus;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class DomainObjectsTest {

  @Test
  void assignHunterToQuestTest() {
    Hunter h = new Hunter(UUID.randomUUID(), "abc", new HashSet<>());
    Quest q =
        new Quest(
            UUID.randomUUID(),
            "ABC",
            "DEF",
            "GHI",
            DangerLevel.LOW,
            QuestStatus.CREATED,
            new HashSet<>());

    q.assignHunter(h);

    assertTrue(q.getAssignedHunters().contains(h));
    assertTrue(h.getAssignedQuests().contains(q));
  }

  @Test
  void assignHunterToQuest_QuestFinished_ThrowsException() {
    Hunter h = new Hunter(UUID.randomUUID(), "abc", new HashSet<>());
    Quest q =
        new Quest(
            UUID.randomUUID(),
            "ABC",
            "DEF",
            "GHI",
            DangerLevel.LOW,
            QuestStatus.FINISHED,
            new HashSet<>());

    assertThrows(WorldLogicException.class, () -> q.assignHunter(h));
  }

  @Test
  void completeChangesQuestState(){
    Quest q =
        new Quest(
            UUID.randomUUID(),
            "ABC",
            "DEF",
            "GHI",
            DangerLevel.LOW,
            QuestStatus.CREATED,
            new HashSet<>());

    q.complete();

    assertEquals(QuestStatus.FINISHED, q.getQuestStatus());
  }

  @Test
  void testDuplicates(){
    UUID u1 = UUID.randomUUID();
    UUID u2 = UUID.randomUUID();

    Hunter h1 = new Hunter(u1, "abc", new HashSet<>());
    Hunter h2 = new Hunter(u1, "abc", new HashSet<>());
    Hunter h3 = new Hunter(u2, "def", new HashSet<>());
    Quest q =
        new Quest(
            UUID.randomUUID(),
            "ABC",
            "DEF",
            "GHI",
            DangerLevel.LOW,
            QuestStatus.CREATED,
            new HashSet<>());

    q.assignHunter(h1);
    q.assignHunter(h2);
    q.assignHunter(h3);

    assertEquals(2, q.getAssignedHunters().size());
  }
}
