package com.aiden.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.BoardDto;

@Mapper
public interface BoardMapper {

	public List<BoardDto> selectBoards(int offset, int limit);
	public List<BoardDto> selectByUserId(Long userId, int offset, int limit);
	public Integer selectCount();
	public Integer selectCountByUserId(Long userId);
	public BoardDto selectByBoardId(Long boardId);
	public Long insertBoard(BoardDto board);
	public Integer updateBoard(Long boardId, BoardDto board);
	public Integer deleteBoard(Long boardId);
}

