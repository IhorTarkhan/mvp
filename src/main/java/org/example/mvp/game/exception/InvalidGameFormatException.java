package org.example.mvp.game.exception;

public class InvalidGameFormatException extends RuntimeException {
  public InvalidGameFormatException(String message) {
    super(message);
  }

  public InvalidGameFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}
