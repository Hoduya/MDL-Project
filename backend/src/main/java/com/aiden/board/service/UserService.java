package com.aiden.board.service;

import java.util.Collections;
import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiden.board.dto.LoginDto;
import com.aiden.board.dto.UserDto;
import com.aiden.board.exception.DuplicatedUsernameException;
import com.aiden.board.exception.LoginFailedException;
import com.aiden.board.exception.UserNotFoundException;
import com.aiden.board.mapper.UserMapper;
import com.aiden.board.utils.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public UserDto join(UserDto userDto) {
		if (userMapper.findByUserId(userDto.getId()).isPresent()) {
			throw new DuplicatedUsernameException("이미 가입된 이메일입니다");
		}
		
		if (userMapper.findByUserName(userDto.getName()).isPresent()) {
			throw new DuplicatedUsernameException("이미 존재하는 이름입니다.");
		}
		
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userDto.setRegDate(new Date());
		userDto.setRole("USER");
		
		userMapper.save(userDto);
		
		return userMapper.findByUserName(userDto.getUsername()).get();
	}

	public String login(LoginDto loginDto) {
		UserDto userDto = userMapper.findByUserId(loginDto.getId())
				.orElseThrow(() -> new LoginFailedException("존재하지 않는 계정입니다"));

		if (!passwordEncoder.matches(loginDto.getPassword(), userDto.getPassword())) {
			throw new LoginFailedException("존재하지 않는 계정입니다");
		}

		return jwtTokenProvider.createToken(userDto.getId(), Collections.singletonList(userDto.getRole()));
	}

	public UserDto findByUserName(String username) {
		return userMapper.findByUserName(username).orElseThrow(() -> new UserNotFoundException("존재하지 않는 계정입니다."));
	}
	
	public UserDto findByUserId(String userId) {
		return userMapper.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("존재하지 않는 계정입니다."));
	}
	
}
