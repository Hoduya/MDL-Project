package com.aiden.board.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.board.BoardDto;

@Mapper
public interface BoardMapper {

	public List<BoardDto> selectBoards(int offset, int limit, int searchFilter, String searchText);
	public Integer selectCount(int searchFilter, String searchText);
	public List<BoardDto> selectByUserId(Long userId, int offset, int limit);
	public Integer selectCountByUserId(Long userId);
	public BoardDto selectByBoardId(Long boardId);
	public Long insertBoard(BoardDto board);
	public Integer updateBoard(Long boardId, BoardDto board);
	public Integer deleteBoard(Long boardId);
}

