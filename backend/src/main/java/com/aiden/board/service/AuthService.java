package com.aiden.board.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiden.board.dto.LoginDto;
import com.aiden.board.dto.UserDto;
import com.aiden.board.dto.jwt.TokenDto;
import com.aiden.board.exception.DuplicatedUsernameException;
import com.aiden.board.exception.LoginFailedException;
import com.aiden.board.mapper.RefreshTokenMapper;
import com.aiden.board.mapper.UserMapper;
import com.aiden.board.utils.JwtProvider;
import com.aiden.board.utils.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserMapper userMapper;
	private final RefreshTokenMapper refreshTokenMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	
	@Transactional
	public UserDto join(UserDto userDto) {
		if (userMapper.findByUserEmail(userDto.getEmail()).isPresent()) {
			throw new DuplicatedUsernameException("이미 가입된 이메일입니다");
		}
		
		if (userMapper.findByUserName(userDto.getName()).isPresent()) {
			throw new DuplicatedUsernameException("이미 존재하는 이름입니다.");
		}
		
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userDto.setRole(Role.ADMIN.getValue());
		
		userMapper.save(userDto);
		
		return userMapper.findByUserEmail(userDto.getEmail()).get();
	}
	
	
	public TokenDto login(LoginDto loginDto) {
		
		// 회원 정보 존재 확인
		UserDto userDto = userMapper.findByUserEmail(loginDto.getEmail())
				.orElseThrow(() -> new LoginFailedException("올바르지 않은 계정정보입니다."));
						
		// 회원 패스워드 일치 정보 확인
		if (!passwordEncoder.matches(loginDto.getPassword(), userDto.getPassword())) {
			throw new LoginFailedException("올바르지 않은 계정정보입니다.");
		}
		
		// Access, Refresh 토큰 생성
		TokenDto tokenDto = jwtProvider.generateToken(userDto.getUserId(), userDto.getRole());
		
		// Refresh 토큰 저장
		RefreshTokenDto refreshToken = RefreshToken.builder()
				.
		
		return tokenDto;
	}
}
