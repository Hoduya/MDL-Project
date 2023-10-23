package com.aiden.board.advice.exception;

public class ExpiredJwtException extends RuntimeException{
	
    public ExpiredJwtException() {
        super();
    }
	
    public ExpiredJwtException(String message) {
        super(message);
    }
}