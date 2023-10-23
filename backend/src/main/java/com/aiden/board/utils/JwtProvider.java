package com.aiden.board.utils;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.aiden.board.dto.Token.TokenDto;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.accessToken-validity-in-seconds}")
	private long accessTokenValidSeconds;

	@Value("${jwt.refreshToken-validity-in-seconds}")
	private long refreshTokenValidSeconds;
	
	private final UserDetailsService userDetailsService;

	@PostConstruct
	protected void init() {
		// 여러 서버 환경에서 시크릿키(문자)가 바이너리 데이터로 바뀌며 발생되는 문제를 방지하기 위해 
		// Base64로 인코딩 된 바이너리 값으로 통일 
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // SecretKey Base64로 인코딩
	}

	// JWT 생성
	public TokenDto createToken(Long userId, String roles) {
		
		// Claims 에 user 구분을 위한 userPK 및 권한 저장 
		Claims claims = Jwts.claims().setSubject(userId.toString());
		claims.put("roles", roles);
		
		// 생성날짜, 만료날짜 
		Date now = new Date();
		
       String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidSeconds))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + refreshTokenValidSeconds))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExprieDate(new Date(now.getTime() + accessTokenValidSeconds))
                .build();
	}

	// JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}


	// 유저 ID 추출
	private String getUserId(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	// Request header에서 token 꺼내옴
	public String resolveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		// 가져온 Authorization Header 가 문자열이고, Bearer 로 시작해야 가져옴
		if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return token.substring(7);
		}

		return null;
	}

	// JWT 토큰 유효성 체크
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException exception) {
			log.info("잘못된 Jwt 서입니다");
		} catch (ExpiredJwtException exception) {
			log.info("만료된 Jwt 토큰입니다");
		} catch (UnsupportedJwtException exception) {
			log.info("지원하지 않는 Jwt 토큰입니다");
		} catch (IllegalArgumentException e) {
			log.error("잘못된 토큰입니다.");
        }

		return false;
	}
}
