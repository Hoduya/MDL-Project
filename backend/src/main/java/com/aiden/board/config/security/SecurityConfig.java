package com.aiden.board.config.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfig {
	private static final String[] PERMIT_URL_ARRAY = { 
			"/v3/api-docs/**", 
			"/swagger-ui/**", 
			"/api/join",
			"/api/login",
			"/api/*"
	};

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("FilterChain");
		http.csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(authorize -> 
			authorize
			.requestMatchers(PERMIT_URL_ARRAY).permitAll()
			.anyRequest().authenticated())
			// 세션을 사용하지 않으므로 STATELESS 설정
        	.sessionManagement(sessionManagement ->
        		sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}
	

	// passwordEncoder
	@Bean
	protected BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
	
	
}