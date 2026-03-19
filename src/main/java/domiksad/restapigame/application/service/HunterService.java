package domiksad.restapigame.application.service;

import domiksad.restapigame.application.exception.HunterNotFound;
import domiksad.restapigame.infrastructure.entity.HunterEntity;
import domiksad.restapigame.infrastructure.repository.HunterRepository;
import domiksad.restapigame.infrastructure.repository.QuestRepository;
import domiksad.restapigame.presentation.dto.HunterRequestDto;
import domiksad.restapigame.presentation.dto.HunterResponseDto;
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
public class HunterService {

  private final HunterRepository hunterRepository;
  private final QuestRepository questRepository;
  private final ModelMapper mapper;

  public HunterResponseDto createHunter(HunterRequestDto hunterDto) {
    HunterEntity hunter = mapper.map(hunterDto, HunterEntity.class);

    HunterEntity savedHunter = hunterRepository.save(hunter);

    return mapper.map(savedHunter, HunterResponseDto.class);
  }

  public List<HunterResponseDto> getAllHunters() {
    return hunterRepository.findAll().stream()
        .map(h -> mapper.map(h, HunterResponseDto.class))
        .toList();
  }

  public HunterResponseDto getHunterById(UUID id) {
    return mapper.map(
        hunterRepository.findById(id).orElseThrow(() -> new HunterNotFound(id)),
        HunterResponseDto.class);
  }

  public void deleteHunterById(UUID id) {
    hunterRepository.delete(
        hunterRepository.findById(id).orElseThrow(() -> new HunterNotFound(id)));
  }

  public List<QuestResponseDto> getHunterAssignedQuestsById(UUID id) {
    return hunterRepository
        .findById(id)
        .orElseThrow(() -> new HunterNotFound(id))
        .getAssignedQuests()
        .stream()
        .map(q -> mapper.map(q, QuestResponseDto.class))
        .toList();
  }

  public HunterResponseDto updateHunter(UUID id, HunterRequestDto hunterDto) {
    HunterEntity hunter = hunterRepository.findById(id).orElseThrow(() -> new HunterNotFound(id));

    hunter.setName(hunterDto.name());

    hunterRepository.save(hunter);
    return mapper.map(hunter, HunterResponseDto.class);
  }
}
