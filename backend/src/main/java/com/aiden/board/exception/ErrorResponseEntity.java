package com.aiden.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class ErrorResponseEntity {
	
	private int status;
	private String name;
	private int code;
	private String message;
	
	public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode e) {
		return ResponseEntity
				.status(e.getHttpStatus())
				.body(ErrorResponseEntity.builder()
						.status(e.getHttpStatus().value())
						.name(e.name())
						.code(e.getCode())
						.message(e.getMessage())
						.build());
	}
}
