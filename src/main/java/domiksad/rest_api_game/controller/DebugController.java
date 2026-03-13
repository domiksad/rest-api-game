package domiksad.rest_api_game.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    @GetMapping("/debug/throw")
    public ResponseEntity<String> throwRuntimeException() {
        if(true)
        throw new RuntimeException("Debug runtime exception");
        else
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Didnt work");
    }
}
