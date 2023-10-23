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

import com.aiden.board.dto.User.UserDto;
import com.aiden.board.exception.DuplicatedUsernameException;
import com.aiden.board.service.CommentService;
import com.aiden.board.dto.ApiResponse.SingleResult;
import com.aiden.board.dto.Token.TokenDto;
import com.aiden.board.dto.Token.TokenRequestDto;
import com.aiden.board.dto.login.LoginRequestDto;
import com.aiden.board.dto.login.LoginResponseDto;
import com.aiden.board.exception.DuplicatedUsernameException;
import com.aiden.board.exception.LoginFailedException;
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
	public SingleResult<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

		LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
		return responseService.getSingleResult(loginResponseDto);
	}
	
	@PostMapping("/join")
	public SingleResult<UserDto> join(@RequestBody UserDto userDto) {
		
		UserDto signupUser = authService.signUp(userDto);
		return responseService.getSingleResult(signupUser);
	}
	
	
	@PostMapping("/reissue")
	public SingleResult<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
		
		TokenDto responseTokenDto = authService.reissue(tokenRequestDto);
		return responseService.getSingleResult(responseTokenDto);
	}
}
