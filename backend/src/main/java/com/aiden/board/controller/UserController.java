package com.aiden.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.aiden.board.dto.LoginDto;
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
@CrossOrigin(origins="http://localhost:8080")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

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

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginDto loginDto) {
    	ResponseEntity<BaseResponse> responseEntity = null;
        try {
            String token = userService.login(loginDto);
            UserDto userDto = userService.findByUserEmail(loginDto.getEmail());
            
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + token);
            
            Map<String, Object> result = new HashMap();
            result.put("token", token);
            result.put("user", userDto);
            
            SingleDataResponse<Map<String, Object>> response = responseService.getSingleDataResponse(true, "로그인 성공", result);
            responseEntity = ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(response);
        } catch (LoginFailedException exception) {
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        return responseEntity;
    }

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
    public ResponseEntity<BaseResponse> getUser(@PathVariable(value="userId") Long userId, final Authentication authentication) {
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
}
