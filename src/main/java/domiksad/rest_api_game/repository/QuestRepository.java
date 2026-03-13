package domiksad.rest_api_game.repository;

import domiksad.rest_api_game.entity.Quest;
import domiksad.rest_api_game.enums.DangerLevel;
import domiksad.rest_api_game.enums.QuestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findAllByQuestStatus(QuestStatus questStatus);
    List<Quest> findAllByDangerLevel(DangerLevel dangerLevel);
    List<Quest> findAllByQuestStatusAndDangerLevel(QuestStatus questStatus, DangerLevel dangerLevel);
}
