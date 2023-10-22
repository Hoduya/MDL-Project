package com.aiden.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.aiden.board.dto.LoginDto;
import com.aiden.board.dto.ProfileDto;
import com.aiden.board.dto.UserDto;
import com.aiden.board.dto.response.BaseResponse;
import com.aiden.board.dto.response.SingleDataResponse;
import com.aiden.board.exception.DuplicatedUsernameException;
import com.aiden.board.exception.LoginFailedException;
import com.aiden.board.exception.UserNotFoundException;
import com.aiden.board.service.ResponseService;
import com.aiden.board.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

	private final UserService userService;
	private final ResponseService responseService;
	private final AuthenticationManager authenticationManager;

	@GetMapping("/users")
	public ResponseEntity<BaseResponse> getCurrentUser(final Authentication authentication) {
		ResponseEntity<BaseResponse> responseEntity = null;
		try {
			Long userId = ((UserDto) authentication.getPrincipal()).getUserId();
			UserDto findUser = userService.findByUserId(userId);

			SingleDataResponse<UserDto> response = responseService.getSingleDataResponse(true, "조회 성공", findUser);

			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (UserNotFoundException exception) {
			BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

			responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}

		return responseEntity;
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<BaseResponse> getUser(@PathVariable(value = "userId") Long userId) {
		ResponseEntity<BaseResponse> responseEntity = null;
		try {
			UserDto findUser = userService.findByUserId(userId);
			SingleDataResponse<UserDto> response = responseService.getSingleDataResponse(true, "조회 성공", findUser);

			responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (UserNotFoundException exception) {
			BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

			responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}

		return responseEntity;
	}

	@PutMapping("/users")
	public ResponseEntity<BaseResponse> updateUser(@RequestBody UserDto user) {
		ResponseEntity<BaseResponse> responseEntity = null;
		try {
			UserDto updateUser = userService.updateUser(user);
			SingleDataResponse<UserDto> response = responseService.getSingleDataResponse(true, "업데이트 성공", updateUser);

			responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (UserNotFoundException exception) {
			BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

			responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}

		return responseEntity;
	}

	@DeleteMapping("/users")
	public ResponseEntity<BaseResponse> deleteUser(@RequestBody UserDto user) {
		ResponseEntity<BaseResponse> responseEntity = null;
		try {
			userService.deleteUser(user.getUserId());
			BaseResponse response = responseService.getBaseResponse(true, "삭제 완료");

			responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception exception) {
			BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

			responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}

		return responseEntity;
	}

	@GetMapping("/profiles/{deptId}")
	public ResponseEntity<BaseResponse> getProfilesFromDepartment(@PathVariable(value = "deptId") Long deptId) {
		ResponseEntity<BaseResponse> responseEntity = null;
		try {
			List<ProfileDto> profiles = userService.selectProfilesFromDepartment(deptId);
			SingleDataResponse<List<ProfileDto>> response = responseService.getSingleDataResponse(true, "조회 성공",
					profiles);

			responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (UserNotFoundException exception) {
			BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

			responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
		}

		return responseEntity;
	}
}
