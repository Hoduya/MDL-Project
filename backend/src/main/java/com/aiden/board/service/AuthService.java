package com.aiden.board.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiden.board.dto.sign.LoginRequestDto;
import com.aiden.board.dto.sign.LoginResponseDto;
import com.aiden.board.dto.sign.SignUpRequestDto;
import com.aiden.board.dto.token.RefreshTokenDto;
import com.aiden.board.dto.token.TokenDto;
import com.aiden.board.dto.token.TokenRequestDto;
import com.aiden.board.dto.user.UserDto;
import com.aiden.board.exception.CustomException;
import com.aiden.board.exception.ErrorCode;
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
		
		// 회원 정보 이메일 존재 확인 
		UserDto userDto = userMapper.findByUserEmail(loginDto.getEmail())
				.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		// 패스워드 일치 여부 확인 
		if (!passwordEncoder.matches(loginDto.getPassword(), userDto.getPassword())) {
			throw new CustomException(ErrorCode.LOGIN_FAILED);
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
			throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
		}
		
		// 유저 이름 중복 확인  
		if (userMapper.findByUserName(signUpRequestDto.getName()).isPresent()) {
			throw new CustomException(ErrorCode.DUPLICATE_USER_NAME);
		}
		
		// 패스워드 암호화 저장 
		signUpRequestDto.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
		userMapper.save(signUpRequestDto);
		UserDto savedUser = userMapper.findByUserEmail(signUpRequestDto.getEmail())
				.orElseThrow(() -> new CustomException(ErrorCode.UNKNOWN_ERROR));
		
		return savedUser; 
	}
	
	/**
	 * 토큰 발급 불가한 경우 (재로그인)
	 * 1. Refresh 토큰 만료
	 * 2. Access 토큰의 페이로드에 포함된 유저 id가 정확한지
	 * 3. 전송된 Refresh 토큰이 기존에 발급했던 토큰과 불일치
	 */
	@Transactional
	public TokenDto reissue(TokenRequestDto tokenRequestDto) { 
        
		// refresh token 검증 (만료 / 유효)
        if (!jwtProvider.validateToken(tokenRequestDto.getRefreshToken(), false)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        // AccessToken 에서 Username (pk) 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        
        // user pk로 유저 검색
        UserDto userDto = userMapper.findByUserId(Long.parseLong(authentication.getName()))
        		.orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));
        
        // 해당 유저에게 발급했던 Refresh Token 조회
        RefreshTokenDto refreshTokenDto = refreshTokenMapper.findByUserId(userDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        // 리프레시 토큰 불일치 에러
        if (!refreshTokenDto.getToken().equals(tokenRequestDto.getRefreshToken())) {
        	throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        
        // AccessToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 저장
        TokenDto newToken = jwtProvider.createToken(userDto.getUserId(), userDto.getRole());
        refreshTokenDto.setToken(newToken.getRefreshToken());
        refreshTokenMapper.save(refreshTokenDto);

        return newToken;
	}
}
