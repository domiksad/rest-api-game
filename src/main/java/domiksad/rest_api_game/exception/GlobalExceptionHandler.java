package domiksad.rest_api_game.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex){
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", ex.getStatusCode().value(),
                "error", ProblemDetail.forStatus(ex.getStatusCode()).getTitle(), // Udalo sie znalezc :D
                "message", ex.getReason()
        );
        return new ResponseEntity<>(body, ex.getStatusCode());
    }
}
