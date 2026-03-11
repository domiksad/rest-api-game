package domiksad.rest_api_game.controller;

import domiksad.rest_api_game.dto.HunterDTO;
import domiksad.rest_api_game.entity.Hunter;
import domiksad.rest_api_game.service.HunterService;
import domiksad.rest_api_game.service.QuestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HunterController {

    @Autowired
    HunterService hunterService;

    @Autowired
    QuestService questService;

    ModelMapper mapper = new ModelMapper();

    @PostMapping("/api/hunters")
    public ResponseEntity<HunterDTO> createHunter(@RequestBody HunterDTO hunterDTO) {
        HunterDTO responseDTO = hunterService.createHunter(hunterDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
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
}
