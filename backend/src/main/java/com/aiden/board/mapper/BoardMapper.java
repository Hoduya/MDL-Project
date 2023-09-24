package com.aiden.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.BoardDto;

@Mapper
public interface BoardMapper {
	
	public List<BoardDto> selectBoards(int offset, int limit);
	public List<BoardDto> selectByWriterId(String writerId);
	public BoardDto selectById(String id);
	public Integer insertBoard(BoardDto board);
	public Integer deleteBoard(String id);
}