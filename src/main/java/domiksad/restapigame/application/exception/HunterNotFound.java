package domiksad.restapigame.application.exception;

public class HunterNotFound extends RuntimeException {
  public HunterNotFound(Long id) {
    super("Hunter with id " + id + " not found");
  }
}
