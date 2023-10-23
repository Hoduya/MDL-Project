package com.aiden.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aiden.board.advice.exception.UserNotFoundException;
import com.aiden.board.dto.user.UserDto;
import com.aiden.board.mapper.UserMapper;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		return userMapper.findByUserId(Long.parseLong(userId))
				.orElseThrow(UserNotFoundException::new);
	}
}
