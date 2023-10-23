package com.aiden.board.controller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiden.board.service.CommentService;
import com.aiden.board.advice.exception.DuplicatedUsernameException;
import com.aiden.board.advice.exception.LoginFailedException;
import com.aiden.board.dto.ApiResponse.SingleDataResponse;
import com.aiden.board.dto.sign.LoginRequestDto;
import com.aiden.board.dto.sign.LoginResponseDto;
import com.aiden.board.dto.sign.SignUpRequestDto;
import com.aiden.board.dto.token.TokenDto;
import com.aiden.board.dto.token.TokenRequestDto;
import com.aiden.board.dto.user.UserDto;
import com.aiden.board.service.AuthService;
import com.aiden.board.service.BoardService;
import com.aiden.board.service.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {

	private final AuthService authService;
	private final ResponseService responseService;

	@PostMapping("/login")
	public SingleDataResponse<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

		LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
		return responseService.getSingleDataResponse(loginResponseDto);
	}
	
	@PostMapping("/join")
	public SingleDataResponse<UserDto> join(@RequestBody SignUpRequestDto signUpRequestDto) {
		
		UserDto signupUser = authService.signUp(signUpRequestDto);
		return responseService.getSingleDataResponse(signupUser);
	}
	
	
	@PostMapping("/reissue")
	public SingleDataResponse<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
		
		TokenDto responseTokenDto = authService.reissue(tokenRequestDto);
		return responseService.getSingleDataResponse(responseTokenDto);
	}
}
