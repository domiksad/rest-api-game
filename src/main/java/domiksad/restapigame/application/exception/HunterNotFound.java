package domiksad.restapigame.application.exception;

import java.util.UUID;

public class HunterNotFound extends RuntimeException {
  public HunterNotFound(UUID id) {
    super("Hunter with id " + id.toString() + " not found");
  }
}
