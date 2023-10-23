package com.aiden.board.advice.exception;

public class RefreshTokenGrantTypeException extends RuntimeException {
	
	public RefreshTokenGrantTypeException() {
        super();
    }
	
    public RefreshTokenGrantTypeException(String message) {
        super(message);
    }
}