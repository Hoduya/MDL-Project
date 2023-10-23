package com.aiden.board.advice.exception;

public class DuplicatedUsernameException extends RuntimeException{

    public DuplicatedUsernameException() {
        super();
    }
	
    public DuplicatedUsernameException(String message) {
        super(message);
    }
}
