package com.aiden.board.exception;

public class DuplicatedUserEmailExceptoin extends RuntimeException{
    public DuplicatedUserEmailExceptoin() {
        super();
    }

    public DuplicatedUserEmailExceptoin(String message) {
        super(message);
    }

    public DuplicatedUserEmailExceptoin(String message, Throwable cause) {
        super(message, cause);
    }
}