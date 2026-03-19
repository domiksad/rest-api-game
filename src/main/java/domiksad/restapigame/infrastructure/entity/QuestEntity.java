package domiksad.restapigame.infrastructure.entity;

import domiksad.restapigame.domain.quest.DangerLevel;
import domiksad.restapigame.domain.quest.QuestStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;
  private String description;
  private String reward;

  @Enumerated(EnumType.STRING)
  private DangerLevel dangerLevel;

  @Enumerated(EnumType.STRING)
  private QuestStatus questStatus = QuestStatus.CREATED;

  @ManyToMany private Set<HunterEntity> assignedHunters;
}
