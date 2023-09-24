package com.aiden.board.config.security;

import com.aiden.board.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *  JWT(Json Web Token) 인증을 처리
 */

// final 필드에 대한 생성자를 자동으로 생성
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

 private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

 private final JwtTokenProvider jwtTokenProvider;

 // Filter
 // - client의 요청을 가로채어 작업을 수행
 // - response 되기 전에 가로채어 작업을 수행
 @Override
 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
         throws IOException, ServletException {
	      
     // HttpServletRequest로 캐스팅하여 JWT 토큰을 해석
     String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

     // 현재 요청의 URI를 가져옵니다.	
     String requestURI = ((HttpServletRequest) request).getRequestURI();

     // 만약 JWT 토큰이 존재하고 유효하다면, 해당 토큰을 사용하여 사용자 인증 정보를 가져와
     // Spring Security의 SecurityContextHolder에 저장합니다.
     if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
         Authentication authentication = jwtTokenProvider.getAuthentication(token);
         SecurityContextHolder.getContext().setAuthentication(authentication);

         // 로그에 메시지를 출력하여 Security context에 인증 정보를 저장했음을 나타냅니다.
         logger.info("Security context에 인증 정보를 저장했습니다, uri: {}", requestURI);
     } else {
         // 유효한 JWT 토큰이 없을 경우, 디버그 로그에 메시지를 출력합니다.
         logger.debug("유효한 Jwt 토큰이 없습니다, uri: {}", requestURI);
     }

     // 다음 필터로 요청을 전달합니다.
     chain.doFilter(request, response);
 }
}
