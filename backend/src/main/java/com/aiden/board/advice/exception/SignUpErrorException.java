package com.aiden.board.advice.exception;

public class SignUpErrorException extends RuntimeException {
	
	public SignUpErrorException() {
        super();
    }
	
    public SignUpErrorException(String message) {
        super(message);
    }
}
