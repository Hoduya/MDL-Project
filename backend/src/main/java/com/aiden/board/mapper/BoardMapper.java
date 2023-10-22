package com.aiden.board.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.BoardDto;

@Mapper
public interface BoardMapper {
	
	List<BoardDto> selectBoards(int offset, int limit, int searchFilter, String searchText);
	Integer selectCount(int searchFilter, String searchText);
	List<BoardDto> selectByUserId(Long userId, int offset, int limit);
	Integer selectCountByUserId(Long userId);
	BoardDto selectByBoardId(Long boardId);
	Long insertBoard(BoardDto board);
	Integer updateBoard(Long boardId, BoardDto board);
	Integer deleteBoard(Long boardId);
}

