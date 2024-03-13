package org.example.mvp.mvp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidGameSetException extends RuntimeException {
    public InvalidGameSetException(String message) {
        super(message);
    }
}
