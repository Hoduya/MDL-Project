package com.aiden.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.DepartmentDto;

@Mapper
public interface DepartmentMapper {
	public List<DepartmentDto> selectDepartments();
}
