package domiksad.rest_api_game.controller;

import domiksad.rest_api_game.dto.HunterDTO;
import domiksad.rest_api_game.dto.QuestDTO;
import domiksad.rest_api_game.enums.DangerLevel;
import domiksad.rest_api_game.enums.QuestStatus;
import domiksad.rest_api_game.service.QuestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestController {

    @Autowired
    QuestService questService;

    @PostMapping("/api/quests")
    public ResponseEntity<QuestDTO> createQuest(@Valid @RequestBody QuestDTO questDTO) {
        QuestDTO responseDTO = questService.createQuest(questDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    @PatchMapping("/api/quests/{id}")
    public ResponseEntity<QuestDTO> updateQuest(
            @PathVariable Long id,
            @Valid @RequestBody QuestDTO questDTO) {

        QuestDTO responseDTO = questService.updateQuest(id, questDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDTO);
    }

    @GetMapping("/api/quests")
    public ResponseEntity<List<QuestDTO>> getAllQuests(
            @RequestParam(required = false)QuestStatus questStatus,
            @RequestParam(required = false)DangerLevel dangerLevel
            ){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(questService.getAllQuestsWithOptionalParams(questStatus, dangerLevel));
    }

    @GetMapping("/api/quests/{id}")
    public ResponseEntity<QuestDTO> getQuestById(@PathVariable("id") Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(questService.getQuestById(id));
    }

    @GetMapping("/api/quests/{id}/hunters")
    public ResponseEntity<List<HunterDTO>> getQuestAssignedHuntersById(@PathVariable("id") Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(questService.getQuestAssignedHuntersById(id));
    }

    @PostMapping("/api/quests/{questId}/hunters/{hunterId}")
    public ResponseEntity<Void> assignQuestToHunter(
            @PathVariable("questId") Long questId,
            @PathVariable("hunterId") Long hunterId
    ){
        questService.assignQuestToHunter(questId, hunterId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/api/quests/{id}")
    public ResponseEntity<Void> deleteQuest(@PathVariable("id") Long id){
        questService.deleteQuestById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/api/quests/{id}/complete")
    public ResponseEntity<QuestDTO> completeTaskById(@PathVariable("id") Long id){
        QuestDTO responseDTO = questService.completeTaskById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDTO);
    }
}
