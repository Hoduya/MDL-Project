package com.aiden.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aiden.board.dto.User.DepartmentDto;
import com.aiden.board.mapper.DepartmentMapper;
import com.aiden.board.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {
	private final DepartmentMapper departmentMapper;
	
	public List<DepartmentDto> selectDepartments() {
		return departmentMapper.selectDepartments();
	}
}
