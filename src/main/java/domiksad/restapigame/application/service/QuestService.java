package domiksad.restapigame.application.service;

import domiksad.restapigame.application.exception.HunterNotFound;
import domiksad.restapigame.application.exception.QuestNotFound;
import domiksad.restapigame.domain.hunter.Hunter;
import domiksad.restapigame.domain.quest.DangerLevel;
import domiksad.restapigame.domain.quest.Quest;
import domiksad.restapigame.domain.quest.QuestStatus;
import domiksad.restapigame.infrastructure.entity.HunterEntity;
import domiksad.restapigame.infrastructure.entity.QuestEntity;
import domiksad.restapigame.infrastructure.repository.HunterRepository;
import domiksad.restapigame.infrastructure.repository.QuestRepository;
import domiksad.restapigame.presentation.dto.HunterResponseDto;
import domiksad.restapigame.presentation.dto.QuestRequestDto;
import domiksad.restapigame.presentation.dto.QuestResponseDto;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class QuestService {

  private final HunterRepository hunterRepository;
  private final QuestRepository questRepository;
  private final ModelMapper mapper;

  public QuestResponseDto createQuest(QuestRequestDto questDTO) {
    QuestEntity quest = mapper.map(questDTO, QuestEntity.class);

    QuestEntity savedQuest = questRepository.save(quest);

    return mapper.map(savedQuest, QuestResponseDto.class);
  }

  public QuestResponseDto getQuestById(UUID id) {
    return mapper.map(
        questRepository.findById(id).orElseThrow(() -> new QuestNotFound(id)),
        QuestResponseDto.class);
  }

  public void deleteQuestById(UUID id) {
    questRepository.delete(questRepository.findById(id).orElseThrow(() -> new QuestNotFound(id)));
  }

  public List<HunterResponseDto> getQuestAssignedHuntersById(UUID id) {
    return questRepository
        .findById(id)
        .orElseThrow(() -> new QuestNotFound(id))
        .getAssignedHunters()
        .stream()
        .map(h -> mapper.map(h, HunterResponseDto.class))
        .toList();
  }

  public void assignQuestToHunter(UUID questId, UUID hunterId) {
    Quest quest =
        mapper.map(
            questRepository.findById(questId).orElseThrow(() -> new QuestNotFound(questId)),
            Quest.class);
    Hunter hunter =
        mapper.map(
            hunterRepository.findById(hunterId).orElseThrow(() -> new HunterNotFound(hunterId)),
            Hunter.class);

    quest.assignHunter(hunter);

    questRepository.save(mapper.map(quest, QuestEntity.class));
    hunterRepository.save(mapper.map(hunter, HunterEntity.class));
  }

  public QuestResponseDto completeTaskById(UUID id) {
    Quest quest = mapper.map(
        questRepository
            .findById(id)
            .orElseThrow(() -> new QuestNotFound(id)), Quest.class);

    quest.complete();

    QuestEntity questEntity = mapper.map(quest, QuestEntity.class);
    questRepository.save(questEntity);

    return mapper.map(questEntity, QuestResponseDto.class);
  }

  public QuestResponseDto updateQuest(UUID id, QuestRequestDto questDto) {
    QuestEntity quest =
        questRepository
            .findById(id)
            .orElseThrow(() -> new QuestNotFound(id));

    quest.setName(questDto.name());
    quest.setDescription(questDto.description());
    quest.setReward(questDto.reward());
    quest.setDangerLevel(questDto.dangerLevel());

    questRepository.save(quest);

    return mapper.map(quest, QuestResponseDto.class);
  }

  // TODO: for more params change to jpa specifications
  public List<QuestResponseDto> getAllQuestsWithOptionalParams(
      QuestStatus questStatus, DangerLevel dangerLevel) {
    List<QuestEntity> quests;
    if (questStatus != null && dangerLevel != null) {
      quests = questRepository.findAllByQuestStatusAndDangerLevel(questStatus, dangerLevel);
    } else if (questStatus != null) {
      quests = questRepository.findAllByQuestStatus(questStatus);
    } else if (dangerLevel != null) {
      quests = questRepository.findAllByDangerLevel(dangerLevel);
    } else {
      quests = questRepository.findAll();
    }

    return quests.stream().map(q -> mapper.map(q, QuestResponseDto.class)).toList();
  }
}
