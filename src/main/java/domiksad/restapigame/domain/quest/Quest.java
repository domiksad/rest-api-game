package domiksad.restapigame.domain.quest;

import domiksad.restapigame.domain.exception.WorldLogicException;
import domiksad.restapigame.domain.hunter.Hunter;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quest {
  private UUID id;

  private String name;
  private String description;
  private String reward;

  private DangerLevel dangerLevel;
  private QuestStatus questStatus = QuestStatus.CREATED;

  private Set<Hunter> assignedHunters;

  public void assignHunter(Hunter hunter) {
    if (questStatus == QuestStatus.FINISHED)
      throw new WorldLogicException("Cant assign hunters to finished quests");
    assignedHunters.add(hunter);
    hunter.assignQuest(this);
  }

  public void complete() {
    questStatus = QuestStatus.FINISHED;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Quest other)) return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
