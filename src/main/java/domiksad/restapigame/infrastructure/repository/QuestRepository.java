package domiksad.restapigame.infrastructure.repository;

import domiksad.restapigame.domain.quest.DangerLevel;
import domiksad.restapigame.domain.quest.QuestStatus;
import domiksad.restapigame.infrastructure.entity.QuestEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends JpaRepository<QuestEntity, UUID> {
    List<QuestEntity> findAllByQuestStatus(QuestStatus questStatus);
    List<QuestEntity> findAllByDangerLevel(DangerLevel dangerLevel);
    List<QuestEntity> findAllByQuestStatusAndDangerLevel(QuestStatus questStatus, DangerLevel dangerLevel);
}
