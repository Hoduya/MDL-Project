package com.aiden.board.advice.exception;

public class LoginFailedException extends RuntimeException{

	public LoginFailedException() {
        super();
    }
	
    public LoginFailedException(String message) {
        super(message);
    }
}
