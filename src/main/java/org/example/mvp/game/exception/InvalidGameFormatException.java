package org.example.mvp.game.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidGameFormatException extends RuntimeException {
  public InvalidGameFormatException(String message) {
    super(message);
  }

  public InvalidGameFormatException(String message, Throwable cause) {
    super(message, cause);
  }
}
