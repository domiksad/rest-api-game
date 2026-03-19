package domiksad.restapigame.presentation.controller;

import domiksad.restapigame.application.service.HunterService;
import domiksad.restapigame.presentation.dto.HunterRequestDto;
import domiksad.restapigame.presentation.dto.HunterResponseDto;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HunterController {

  private final HunterService hunterService;

  @PostMapping("/api/hunters")
  public ResponseEntity<HunterResponseDto> createHunter(
      @Valid @RequestBody HunterRequestDto hunterDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(hunterService.createHunter(hunterDto));
  }

  @PutMapping("/api/hunters/{id}")
  public ResponseEntity<HunterResponseDto> updateHunter(
      @PathVariable UUID id, @Valid @RequestBody HunterRequestDto hunterDTO) {

    return ResponseEntity.status(HttpStatus.OK).body(hunterService.updateHunter(id, hunterDTO));
  }

  @GetMapping("/api/hunters")
  public ResponseEntity<List<HunterResponseDto>> getAllHunters() {
    return ResponseEntity.status(HttpStatus.OK).body(hunterService.getAllHunters());
  }

  @GetMapping("/api/hunters/{id}")
  public ResponseEntity<HunterResponseDto> getHunterById(@PathVariable("id") UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(hunterService.getHunterById(id));
  }

  @GetMapping("/api/hunters/{id}/quests")
  public ResponseEntity<List<QuestResponseDto>> getHunterAssignedQuestsById(
      @PathVariable("id") UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(hunterService.getHunterAssignedQuestsById(id));
  }

  @DeleteMapping("/api/hunters/{id}")
  public ResponseEntity<Void> deleteHunter(@PathVariable("id") UUID id) {
    hunterService.deleteHunterById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
