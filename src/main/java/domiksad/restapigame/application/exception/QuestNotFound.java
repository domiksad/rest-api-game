package domiksad.restapigame.application.exception;

import java.util.UUID;

public class QuestNotFound extends RuntimeException {
  public QuestNotFound(UUID id) {
    super("Quest with id " + id.toString() + " not found");

  }
}
