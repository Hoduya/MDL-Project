package com.aiden.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.MenuImage.MenuImageDto;

@Mapper
public interface MenuImageMapper {
	void saveMenuImageDto(MenuImageDto image);
	MenuImageDto selectMenuImageDto(String storedName);
}
