package com.aiden.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.component.ComponentDto;
import com.aiden.board.dto.component.UpdateComponentDto;

@Mapper
public interface ComponentMapper {
	void createTuple(Long userId, Integer deptId);
	Integer updatePosition(UpdateComponentDto component);
	void initializeVoteState();
	List<ComponentDto> selectByDeptId(Integer deptId);
}
