package domiksad.rest_api_game.repository;

import domiksad.rest_api_game.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
}
