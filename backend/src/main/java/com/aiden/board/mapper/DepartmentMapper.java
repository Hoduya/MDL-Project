package com.aiden.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.user.DepartmentDto;

@Mapper
public interface DepartmentMapper {
	
	List<DepartmentDto> selectDepartments();
	String selectDepartmentName(Integer deptId);
}
