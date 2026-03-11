package domiksad.rest_api_game.repository;

import domiksad.rest_api_game.entity.Hunter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HunterRepository extends JpaRepository<Hunter, Long> {
}
