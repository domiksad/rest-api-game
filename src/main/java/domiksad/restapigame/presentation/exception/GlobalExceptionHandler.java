package domiksad.restapigame.presentation.exception;

import domiksad.restapigame.application.exception.HunterNotFound;
import domiksad.restapigame.application.exception.QuestNotFound;
import domiksad.restapigame.domain.exception.WorldLogicException;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex){
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", ex.getStatusCode().value(),
                "error", Objects.requireNonNullElse(ProblemDetail.forStatus(ex.getStatusCode()).getTitle(),"")
        );
        return new ResponseEntity<>(body, ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> MethodArgumentNotValidException(MethodArgumentNotValidException ex){
        HttpStatus code = HttpStatus.BAD_REQUEST;

        Map<String, String> fieldsErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> Objects.requireNonNullElse(error.getDefaultMessage(), "")
                ));

        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", code.value(),
                "error", Objects.requireNonNullElse(ProblemDetail.forStatus(code).getTitle(),""),
                    "errors", fieldsErrors
        );
        return new ResponseEntity<>(body, code);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        HttpStatus code = HttpStatus.BAD_REQUEST;

        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", code.value(),
                "error", "Parameter " + ex.getName() + " must be of type " + ex.getRequiredType().getSimpleName()
        );
        return new ResponseEntity<>(body, code);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex){
        logger.warning(ex.getMessage());

        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;

        Map<String, Object> body = Map.of(
                "timestamp", Instant.now(),
                "status", code.value(),
                "error", Objects.requireNonNullElse(ProblemDetail.forStatus(code).getTitle(),"")
        );
        return new ResponseEntity<>(body, code);
    }

  @ExceptionHandler({QuestNotFound.class, HunterNotFound.class})
  public ResponseEntity<Map<String, Object>> handleNotFound(Exception ex) {
    logger.warning(ex.getMessage());

    HttpStatus code = HttpStatus.NOT_FOUND;

    Map<String, Object> body = Map.of(
        "timestamp", Instant.now(),
        "status", code.value(),
        "error", ex.getMessage()
    );

    return new ResponseEntity<>(body, code);
  }

  @ExceptionHandler(WorldLogicException.class)
  public ResponseEntity<Map<String, Object>> handleWorldLogic(WorldLogicException ex) {
    logger.warning(ex.getMessage());

    HttpStatus code = HttpStatus.BAD_REQUEST;

    Map<String, Object> body = Map.of(
        "timestamp", Instant.now(),
        "status", code.value(),
        "error", ex.getMessage()
    );

    return new ResponseEntity<>(body, code);
  }
}
