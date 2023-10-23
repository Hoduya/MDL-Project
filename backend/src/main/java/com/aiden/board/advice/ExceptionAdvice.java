package com.aiden.board.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aiden.board.advice.exception.DuplicatedUserEmailExceptoin;
import com.aiden.board.advice.exception.DuplicatedUsernameException;
import com.aiden.board.advice.exception.SignUpErrorException;
import com.aiden.board.advice.exception.TokenNotFoundException;
import com.aiden.board.dto.ApiResponse.CommonResponse;
import com.aiden.board.service.response.ResponseService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
	
	private final ResponseService responseService;
	
    /**
     * default Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse defaultException(HttpServletRequest request, Exception e) {
        return responseService.getErrorResponse("알 수 없는 오류가 발생하였습니다.");
    }
	
    /**
     * 회원가입 - 에러
     */
    @ExceptionHandler(SignUpErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse signUpErrorExceptoin(HttpServletRequest request, SignUpErrorException e) {
        return responseService.getErrorResponse("회원가입 중 오류가 발생하였습니다");
    }
    
	
	/**
	 * 회원가입 - 이메일 중복 예외
	 */
    @ExceptionHandler(DuplicatedUserEmailExceptoin.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected CommonResponse existUserEmailException(HttpServletRequest request, DuplicatedUserEmailExceptoin e) {
        return responseService.getFailResponse("이미 가입된 이메일 입니다");
    }
    
	/**
	 * 회원가입 - 이름 중복 예외
	 */
    @ExceptionHandler(DuplicatedUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected CommonResponse existUserNameException(HttpServletRequest request, DuplicatedUsernameException e) {
        return responseService.getFailResponse("이미 가입된 사용자 이름입니다");
    }
    
    /**
     * 토큰 - 요청 헤더에 토큰 없음 
     */
    @ExceptionHandler(TokenNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected CommonResponse tokenNotFoundException(HttpServletRequest request, TokenNotFoundException e) {
        return responseService.getFailResponse("요청 헤더에 토큰이 포함되지 않았습니다");
    }
 }
