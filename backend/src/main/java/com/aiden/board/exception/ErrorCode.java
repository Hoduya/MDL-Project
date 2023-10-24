package com.aiden.board.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 100, "알 수 없는 오류입니다. 잠시후 다시 시도해주세요"),
	
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, 101, "사용자를 찾을 수 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, 102, "존재하는 이메일입니다."),
    DUPLICATE_USER_NAME(HttpStatus.CONFLICT, 103, "존재하는 회원이름입니다"),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, 104, "로그인 정보가 올바르지 않습니다"),
	
	ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 105, "엑세스 토큰 만료"),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 106, "유효하지 않은 토큰");
    
    private final HttpStatus httpStatus;	// HttpStatus
    private final int code;				// ACCOUNT-001
    private final String message;			// 설명
}