package domiksad.restapigame.application.exception;

public class QuestNotFound extends RuntimeException {
  public QuestNotFound(Long id) {
    super("Quest with id " + id + " not found");

  }
}
