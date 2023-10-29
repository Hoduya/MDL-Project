package com.aiden.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aiden.board.dto.MenuImage.MenuImageDto;
import com.aiden.board.dto.user.DepartmentDto;
import com.aiden.board.mapper.DepartmentMapper;
import com.aiden.board.mapper.MenuImageMapper;
import com.aiden.board.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuImageService {
	
	private final MenuImageMapper menuImageMapper;
	
	public void saveMenuImageDto(MenuImageDto imageDto) {
		
		menuImageMapper.saveMenuImageDto(imageDto);
	}
	
	public MenuImageDto selectMenuImageDto(String storedName) {
		
		return menuImageMapper.selectMenuImageDto(storedName);
	}
}