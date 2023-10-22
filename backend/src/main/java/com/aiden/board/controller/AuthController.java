package com.aiden.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiden.board.dto.LoginDto;
import com.aiden.board.dto.UserDto;
import com.aiden.board.dto.response.BaseResponse;
import com.aiden.board.dto.response.SingleDataResponse;
import com.aiden.board.exception.DuplicatedUsernameException;
import com.aiden.board.service.CommentService;
import com.aiden.board.service.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
	
	// 회원가입
	// 성공 여부만 리턴. 로그인 해야 토큰 생성
	@PostMapping("/join")
	public ResponseEntity<BaseResponse> join(@RequestBody UserDto userDto) {
		ResponseEntity<BaseResponse> responseEntity = null;
		try {
			UserDto savedUser = userService.join(userDto);
			SingleDataResponse<UserDto> response = responseService.getSingleDataResponse(true, "회원가입 성공", savedUser);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (DuplicatedUsernameException exception) {
			log.debug(exception.getMessage());
			BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

			responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}
		return responseEntity;
	}
	
	// 로그인
	// Email, PW 검증 후 Access / Refresh 토큰 발급.
	// Refresh 토큰은 Refresh 토큰 테이블에 별도로 저장.
	@PostMapping("/login")
	public ResponseEntity<BaseResponse> login(@RequestBody LoginDto loginDto) {
		
		// 유저 계정 존재 &  패스워드 확인 후 토큰 생성.
		String token = userService.login(loginDto);
		
		Map<String, Object> result = new HashMap();
		result.put("token", token);
		result.put("user", userDto);

		SingleDataResponse<Map<String, Object>> response = responseService.getSingleDataResponse(true, "로그인 성공",
				result);

		ResponseEntity<BaseResponse> responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
		return responseEntity;
	}
}
