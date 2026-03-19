package domiksad.restapigame.presentation.controller;

import domiksad.restapigame.application.service.QuestService;
import domiksad.restapigame.domain.quest.DangerLevel;
import domiksad.restapigame.domain.quest.QuestStatus;
import domiksad.restapigame.presentation.dto.HunterResponseDto;
import domiksad.restapigame.presentation.dto.QuestRequestDto;
import domiksad.restapigame.presentation.dto.QuestResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class QuestController {

  private final QuestService questService;

  @PostMapping("/api/quests")
  public ResponseEntity<QuestResponseDto> createQuest(
      @Valid @RequestBody QuestRequestDto questDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(questService.createQuest(questDTO));
  }

  @PutMapping("/api/quests/{id}")
  public ResponseEntity<QuestResponseDto> updateQuest(
      @PathVariable UUID id, @Valid @RequestBody QuestRequestDto questDTO) {
    return ResponseEntity.status(HttpStatus.OK).body(questService.updateQuest(id, questDTO));
  }

  @GetMapping("/api/quests")
  public ResponseEntity<List<QuestResponseDto>> getAllQuests(
      @RequestParam(required = false) QuestStatus questStatus,
      @RequestParam(required = false) DangerLevel dangerLevel) {

    return ResponseEntity.status(HttpStatus.OK)
        .body(questService.getAllQuestsWithOptionalParams(questStatus, dangerLevel));
  }

  @GetMapping("/api/quests/{id}")
  public ResponseEntity<QuestResponseDto> getQuestById(@PathVariable("id") UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(questService.getQuestById(id));
  }

  @GetMapping("/api/quests/{id}/hunters")
  public ResponseEntity<List<HunterResponseDto>> getQuestAssignedHuntersById(
      @PathVariable("id") UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(questService.getQuestAssignedHuntersById(id));
  }

  @PostMapping("/api/quests/{questId}/hunters/{hunterId}")
  public ResponseEntity<Void> assignQuestToHunter(
      @PathVariable("questId") UUID questId, @PathVariable("hunterId") UUID hunterId) {
    questService.assignQuestToHunter(questId, hunterId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("/api/quests/{id}")
  public ResponseEntity<Void> deleteQuest(@PathVariable("id") UUID id) {
    questService.deleteQuestById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PostMapping("/api/quests/{id}/complete")
  public ResponseEntity<QuestResponseDto> completeTaskById(@PathVariable("id") UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(questService.completeTaskById(id));
  }
}
