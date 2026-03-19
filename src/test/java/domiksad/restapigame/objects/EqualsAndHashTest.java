package domiksad.restapigame.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import domiksad.restapigame.domain.hunter.Hunter;
import domiksad.restapigame.domain.quest.DangerLevel;
import domiksad.restapigame.domain.quest.Quest;
import domiksad.restapigame.domain.quest.QuestStatus;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class EqualsAndHashTest {
  @Test
  void testQuestEqualsAndHashCode() {
    final UUID u1 = UUID.randomUUID();
    final UUID u2 = UUID.randomUUID();

    Quest q1 =
        new Quest(
            u1,
            "Quest1",
            "Desc1",
            "Reward1",
            DangerLevel.LOW,
            QuestStatus.CREATED,
            new HashSet<>());
    Quest q2 =
        new Quest(
            u1,
            "Quest1",
            "Desc1",
            "Reward1",
            DangerLevel.LOW,
            QuestStatus.CREATED,
            new HashSet<>());
    Quest q3 =
        new Quest(
            u2,
            "Quest2",
            "Desc2",
            "Reward2",
            DangerLevel.MEDIUM,
            QuestStatus.FINISHED,
            new HashSet<>());

    assertEquals(q1, q2);
    assertNotEquals(q1, q3);

    assertEquals(q1.hashCode(), q2.hashCode());
    assertNotEquals(q1.hashCode(), q3.hashCode());

    HashSet<Quest> set = new HashSet<>();
    set.add(q1);
    set.add(q2);
    set.add(q3);
    assertEquals(2, set.size());
  }

  @Test
  void testHunterEqualsAndHashCode() {
    UUID u1 = UUID.randomUUID();
    UUID u2 = UUID.randomUUID();

    Hunter h1 = new Hunter(u1, "Alice", new HashSet<>());
    Hunter h2 = new Hunter(u1, "Alice", new HashSet<>());
    Hunter h3 = new Hunter(u2, "Bob", new HashSet<>());

    assertEquals(h1, h2);
    assertNotEquals(h1, h3);

    assertEquals(h1.hashCode(), h2.hashCode());
    assertNotEquals(h1.hashCode(), h3.hashCode());

    HashSet<Hunter> set = new HashSet<>();
    set.add(h1);
    set.add(h2);
    set.add(h3);
    assertEquals(2, set.size());
  }
}
