package com.aiden.board.advice.exception;

public class InvalidRefreshTokenException extends RuntimeException {
    
	public InvalidRefreshTokenException() {
        super();
    }
	
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}