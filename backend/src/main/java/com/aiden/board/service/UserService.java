package com.aiden.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aiden.board.dto.user.ProfileDto;
import com.aiden.board.dto.user.UserDto;
import com.aiden.board.exception.CustomException;
import com.aiden.board.exception.ErrorCode;
import com.aiden.board.mapper.DepartmentMapper;
import com.aiden.board.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserMapper userMapper;
	private final DepartmentMapper departmentMapper;
	
	public UserDto findByUserId(Long userId) {
		return userMapper.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));
	}
	
	public UserDto findByUserEmail(String email) {
		return userMapper.findByUserEmail(email).orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));
	}
	
	public UserDto findByUserName(String username) {
		return userMapper.findByUserName(username).orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));
	}
	
	public List<ProfileDto> selectProfilesFromDepartment(Long deptId) {
		return userMapper.selectProfilesFromDepartment(deptId);
	}
	
	public UserDto updateUser(UserDto userDto) {
		
		String deptName = departmentMapper.selectDepartmentName(userDto.getDeptId());
		userDto.setDeptName(deptName);
		userMapper.updateUser(userDto);
		return findByUserId(userDto.getUserId());
	}
	
	public void deleteUser(Long userId) {
		Integer result = userMapper.deleteUser(userId);
		if(result < 1) throw new CustomException(ErrorCode.UNKNOWN_ERROR);
	}
}
