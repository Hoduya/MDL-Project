package com.aiden.board.config.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.aiden.board.utils.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
    private final JwtProvider jwtProvider;
		
	private static final String[] PERMIT_GET_URLS = {  
			"/api/boards",
			"/api/boards/**",
			"/api/users/**",
			"/api/departments/**"
	};
	
	private static final String[] PERMIT_ALL_URLS = { 
			"/api/join",
			"/api/login",
			"/api/reissue",
			"/swagger-ui/**",
			"/v3/api-docs/**"
	};

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement( // 세션을 사용하지 않으므로 STATELESS 설정
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		
			.authorizeHttpRequests(authorize -> 
				authorize
					.requestMatchers(HttpMethod.GET, PERMIT_GET_URLS).permitAll()
					.requestMatchers(PERMIT_ALL_URLS).permitAll()
					.anyRequest().authenticated())		
		
		 	// 토큰 검증 완료 시 ID/PW 인증 단계 필터를 건너뜀.
		 	.addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JwtExceptionFilter(new ObjectMapper()), JwtFilter.class);

		return http.build();
	}

	// passwordEncoder
	// Bcypt 암호화 단방향 해시 알고리즘
	@Bean
	protected BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
}