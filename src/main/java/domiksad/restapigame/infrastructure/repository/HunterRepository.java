package domiksad.restapigame.infrastructure.repository;

import domiksad.restapigame.infrastructure.entity.HunterEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HunterRepository extends JpaRepository<HunterEntity, UUID> {
}
