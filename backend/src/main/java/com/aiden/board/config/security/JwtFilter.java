package com.aiden.board.config.security;

import com.aiden.board.utils.JwtProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * JWT(Json Web Token) 인증을 처리
 */

// final 필드에 대한 생성자를 자동으로 생성
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

	private final JwtProvider jwtTokenProvider;

	// Filter
	// - client의 요청을 가로채어 작업을 수행
	// - response 되기 전에 가로채어 작업을 수행
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		
		String requestURI = ((HttpServletRequest) request).getRequestURI();
		
		// 만약 JWT 토큰이 존재하고 유효하다면, 해당 토큰을 사용하여 사용자 인증 정보를 가져와
		// Spring Security의 SecurityContextHolder에 저장
		if (token != null && jwtTokenProvider.validateToken(token, true)) {
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			logger.info("Security context에 인증 정보를 저장했습니다, uri: {}", requestURI);
		} 

		// 다음 필터로 요청을 전달합니다.
		filterChain.doFilter(request, response);
	}
}
