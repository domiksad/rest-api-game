package domiksad.rest_api_game.controller;

import domiksad.rest_api_game.dto.HunterDTO;
import domiksad.rest_api_game.dto.QuestDTO;
import domiksad.rest_api_game.service.HunterService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HunterController {

    @Autowired
    HunterService hunterService;

    @PostMapping("/api/hunters")
    public ResponseEntity<HunterDTO> createHunter(@Valid @RequestBody HunterDTO hunterDTO) {
        HunterDTO responseDTO = hunterService.createHunter(hunterDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    @PatchMapping("/api/hunters/{id}")
    public ResponseEntity<HunterDTO> updateHunter(
            @PathVariable Long id,
            @Valid @RequestBody HunterDTO hunterDTO) {

        HunterDTO responseDTO = hunterService.updateHunter(id, hunterDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDTO);
    }

    @GetMapping("/api/hunters")
    public ResponseEntity<List<HunterDTO>> getAllHunters(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hunterService.getAllHunters());
    }

    @GetMapping("/api/hunters/{id}")
    public ResponseEntity<HunterDTO> getHunterById(@PathVariable("id") Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hunterService.getHunterById(id));
    }

    @GetMapping("/api/hunters/{id}/quests")
    public ResponseEntity<List<QuestDTO>> getHunterAssignedQuestsById(@PathVariable("id") Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hunterService.getHunterAssignedQuestsById(id));
    }

    @DeleteMapping("/api/hunters/{id}")
    public ResponseEntity<Void> deleteHunter(@PathVariable("id") Long id){
        hunterService.deleteHunterById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
