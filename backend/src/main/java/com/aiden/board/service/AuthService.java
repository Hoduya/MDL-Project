package com.aiden.board.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiden.board.advice.exception.DuplicatedUserEmailExceptoin;
import com.aiden.board.advice.exception.DuplicatedUsernameException;
import com.aiden.board.advice.exception.LoginFailedException;
import com.aiden.board.advice.exception.RefreshTokenException;
import com.aiden.board.advice.exception.SignUpErrorException;
import com.aiden.board.advice.exception.UserNotFoundException;
import com.aiden.board.dto.sign.LoginRequestDto;
import com.aiden.board.dto.sign.LoginResponseDto;
import com.aiden.board.dto.sign.SignUpRequestDto;
import com.aiden.board.dto.token.RefreshTokenDto;
import com.aiden.board.dto.token.TokenDto;
import com.aiden.board.dto.token.TokenRequestDto;
import com.aiden.board.dto.user.UserDto;
import com.aiden.board.mapper.RefreshTokenMapper;
import com.aiden.board.mapper.UserMapper;
import com.aiden.board.utils.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

	private final UserMapper userMapper;
	private final RefreshTokenMapper refreshTokenMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	@Transactional
	public LoginResponseDto login(LoginRequestDto loginDto) {
		
		// 회원 정보 존재 확인 
		UserDto userDto = userMapper.findByUserEmail(loginDto.getEmail())
				.orElseThrow(LoginFailedException::new);

		// 패스워드 일치 여부 확인 
		if (!passwordEncoder.matches(loginDto.getPassword(), userDto.getPassword())) {
			throw new LoginFailedException();
		}
		
		// Access & Refresh 토큰 발급
		TokenDto tokenDto = jwtProvider.createToken(userDto.getUserId(), userDto.getRole());
		
		RefreshTokenDto refreshTokenDto = RefreshTokenDto.builder()
				.userId(userDto.getUserId())
				.token(tokenDto.getRefreshToken())
				.build();
		refreshTokenMapper.save(refreshTokenDto);
		
		return LoginResponseDto.builder()
				.user(userDto)
				.token(tokenDto)
				.build();
	}
	
	public UserDto signUp(SignUpRequestDto signUpRequestDto) {
		
		// 이메일 중복 확인 
		if (userMapper.findByUserEmail(signUpRequestDto.getEmail()).isPresent()) {
			throw new DuplicatedUserEmailExceptoin();
		}
		
		// 유저 이름 중복 확인  
		if (userMapper.findByUserName(signUpRequestDto.getName()).isPresent()) {
			throw new DuplicatedUsernameException();
		}
		
		// 패스워드 암호화 저장 
		signUpRequestDto.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
		userMapper.save(signUpRequestDto);
		UserDto savedUser = userMapper.findByUserEmail(signUpRequestDto.getEmail())
				.orElseThrow(SignUpErrorException::new);
		
		return savedUser; 
	}
	
	@Transactional
	public TokenDto reissue(TokenRequestDto tokenRequestDto) { 
        
		// 만료된 refresh token 에러 (재로그인)
        if (!jwtProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RefreshTokenException();
        }

        // AccessToken 에서 Username (pk) 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        // user pk로 유저 검색
        UserDto userDto = userMapper.findByUserId(Long.parseLong(authentication.getName()))
        		.orElseThrow(UserNotFoundException::new);
        
        // 유저한테 발급된 Refresh Token 이 없음 (재로그인)
        RefreshTokenDto refreshTokenDto = refreshTokenMapper.findByUserId(userDto.getUserId())
                .orElseThrow(RefreshTokenException::new);

        // 리프레시 토큰 불일치 에러
        if (!refreshTokenDto.getToken().equals(tokenRequestDto.getRefreshToken())) {
        	throw new RefreshTokenException();
        }
        
        // AccessToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 저장
        TokenDto newToken = jwtProvider.createToken(userDto.getUserId(), userDto.getRole());
        refreshTokenDto.setToken(newToken.getRefreshToken());
        refreshTokenMapper.save(refreshTokenDto);

        return newToken;
	}
}
