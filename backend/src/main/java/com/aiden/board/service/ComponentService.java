package com.aiden.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aiden.board.dto.component.ComponentDto;
import com.aiden.board.dto.component.UpdateComponentDto;
import com.aiden.board.exception.CustomException;
import com.aiden.board.exception.ErrorCode;
import com.aiden.board.mapper.ComponentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComponentService {
	
	private final ComponentMapper componentMapper;
	
	public void updatePosition(UpdateComponentDto componentDto) {
		Integer result = componentMapper.updatePosition(componentDto);
		if(result <= 0) { throw new CustomException(ErrorCode.UNKNOWN_ERROR); };
	}
	
	public List<ComponentDto> selectByDeptId(Integer deptId) {
		List<ComponentDto> components = componentMapper.selectByDeptId(deptId);
		log.info(deptId.toString());
		log.info(String.valueOf(components.size()));
		return components;
	}
}
