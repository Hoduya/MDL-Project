package com.aiden.board.advice.exception;

public class DuplicatedUserEmailExceptoin extends RuntimeException{

	public DuplicatedUserEmailExceptoin() {
        super();
    }
	
    public DuplicatedUserEmailExceptoin(String message) {
        super(message);
    }
}