package com.aiden.board.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiden.board.dto.User.ProfileDto;
import com.aiden.board.dto.User.UserDto;
import com.aiden.board.dto.login.LoginRequestDto;
import com.aiden.board.exception.DuplicatedUsernameException;
import com.aiden.board.exception.LoginFailedException;
import com.aiden.board.exception.UserNotFoundException;
import com.aiden.board.mapper.DepartmentMapper;
import com.aiden.board.mapper.UserMapper;
import com.aiden.board.utils.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserMapper userMapper;
	private final DepartmentMapper departmentMapper;
	private final JwtProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;
	
	public UserDto findByUserId(Long userId) {
		return userMapper.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("존재하지 않는 계정입니다."));
	}
	
	public UserDto findByUserEmail(String email) {
		return userMapper.findByUserEmail(email).orElseThrow(() -> new UserNotFoundException("존재하지 않는 계정입니다."));
	}
	
	public UserDto findByUserName(String username) {
		return userMapper.findByUserName(username).orElseThrow(() -> new UserNotFoundException("존재하지 않는 이름입니다."));
	}
	
	public List<ProfileDto> selectProfilesFromDepartment(Long deptId) {
		return userMapper.selectProfilesFromDepartment(deptId);
	}
	
	public UserDto updateUser(UserDto userDto) {
		
		String deptName = departmentMapper.selectDepartmentName(userDto.getDeptId());
		userDto.setDeptName(deptName);
		userMapper.updateUser(userDto);
		return userMapper.findByUserId(userDto.getUserId()).orElseThrow(() -> new UserNotFoundException("존재하지 않는 계정입니다."));
	}
	
	public void deleteUser(Long userId) {
		Integer result = userMapper.deleteUser(userId);
		if(result < 1) { throw new UserNotFoundException("유저 삭제 중 오류가 발생했습니다."); }
	}
}
