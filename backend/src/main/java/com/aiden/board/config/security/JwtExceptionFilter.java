package com.aiden.board.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aiden.board.exception.CustomException;
import com.aiden.board.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
	
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
        try {
        	filterChain.doFilter(request, response);
        } catch (CustomException exception) {            
        	
        	// 엑세스 토큰 만료 & 리프레시 토큰 유효
            if(exception.getErrorCode().equals(ErrorCode.ACCESS_TOKEN_EXPIRED)) {
                setResponse(response, exception.getErrorCode());
            }
            // 그 외 모든 경우 재로그인
            else {
                setResponse(response, exception.getErrorCode());
            }
        }
	}

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws RuntimeException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());
        
        Map<String, Object> map = new HashMap<>();
        map.put("status", errorCode.getHttpStatus().value());
        map.put("code", errorCode.getCode());
        map.put("message", errorCode.getMessage());
        
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }

}