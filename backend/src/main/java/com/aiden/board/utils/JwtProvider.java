package com.aiden.board.utils;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.aiden.board.dto.jwt.TokenDto;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.aceessToken-validity-in-seconds}")
	private long accessTokenValidSeconds;
	
	@Value("${jwt.refreshToken-validity-in-seconds}")
	private long refreshTokenValidSeconds;

	private final UserDetailsService userDetailsService;
	private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@PostConstruct
	protected void init() {
		// 여러 서버 환경에서 시크릿키(문자)가 바이너리 데이터로 바뀌며 발생되는 문제를 방지하기 위해 
		// Base64로 인코딩 된 바이너리 값으로 통일 
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // SecretKey Base64로 인코딩
	}

	// JWT 토큰 생성
	public TokenDto generateToken(Long userPk, String role) {
		
		// 토큰 식별을 위해 claim 에 유저 id, 권한 삽입
		Claims claims = Jwts.claims().setSubject(String.valueOf(userPk));
		claims.put("roles", role);
		
		Date now = new Date();
		
		String accessToken = Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + accessTokenValidSeconds * 1000))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
		
		String refreshToken = Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + refreshTokenValidSeconds * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
		
		return TokenDto.builder()
				.grantType("Bearer")
				.accessToken(accessToken)
				.accessToken(refreshToken)
				.accessTokenExpireDate(new Date(now.getTime() + accessTokenValidSeconds * 1000))
				.build();
	}

	// JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	// 토큰에서 유저 ID 추출
	public String getUserId(String token) {
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
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

			return !claims.getBody().getExpiration().before(new Date());
		} catch (SecurityException | MalformedJwtException | IllegalArgumentException exception) {
			logger.info("잘못된 Jwt 토큰입니다");
		} catch (ExpiredJwtException exception) {
			logger.info("만료된 Jwt 토큰입니다");
		} catch (UnsupportedJwtException exception) {
			logger.info("지원하지 않는 Jwt 토큰입니다");
		}

		return false;
	}
}
