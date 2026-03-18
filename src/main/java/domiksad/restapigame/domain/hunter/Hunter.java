package domiksad.restapigame.domain.hunter;

import domiksad.restapigame.domain.quest.Quest;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hunter {
  private Long id;

  private String name;

  private Set<Quest> assignedQuests;

  public void assignQuest(Quest quest) {
    assignedQuests.add(quest);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Hunter other)) return false;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
