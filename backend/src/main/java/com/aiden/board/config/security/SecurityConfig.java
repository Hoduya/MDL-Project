package com.aiden.board.config.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

import com.aiden.board.utils.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
    private final JwtTokenProvider jwtTokenProvider;
	
	private static final String[] PERMIT_ALL_URLS = { 
			"/api/join",
			"/api/login",
			"/swagger-ui/**",
			"/v3/api-docs/**"
	};
	
	private static final String[] PERMIT_GET_URLS = {  
			"/api/boards/**",
			"/api/users/**",
			"/api/departments/**"
	};
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(authorize -> 
			authorize
			.requestMatchers(HttpMethod.GET, PERMIT_GET_URLS).permitAll()
			.requestMatchers(PERMIT_ALL_URLS).permitAll()
			.anyRequest().authenticated())
			// 세션을 사용하지 않으므로 STATELESS 설정
        	.sessionManagement(sessionManagement ->
        		sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
        http
        	.addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); 

		return http.build();
	}
	

	// passwordEncoder
	@Bean
	protected BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
}