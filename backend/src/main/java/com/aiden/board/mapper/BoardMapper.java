package com.aiden.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.BoardDto;

@Mapper
public interface BoardMapper {

	public List<BoardDto> selectBoards(int offset, int limit);
	public List<BoardDto> selectByUserName(String username, int offset, int limit);
	public Integer selectCount();
	public Integer selectCountByUserName(String username);
	public BoardDto selectByBno(Long bno);
	public Long insertBoard(BoardDto board);
	public Integer updateBoard(String bno, BoardDto board);
	public Integer deleteBoard(String bno);
}

