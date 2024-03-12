package org.example.mvp.game.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidGameFormatException extends RuntimeException {
    public InvalidGameFormatException(Throwable cause) {
        super(cause);
    }
}
