package com.aiden.board.service;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiden.board.dto.BoardDto;
import com.aiden.board.dto.UserDto;
import com.aiden.board.mapper.BoardMapper;
import com.aiden.board.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private static final Logger log = LoggerFactory.getLogger(BoardService.class);
	
	private final BoardMapper boardMapper;
	private final UserMapper userMapper;
	
	public List<BoardDto> selectBoards(int offset, int limit, int searchFilter, String searchText) {
		List<BoardDto> boards = boardMapper.selectBoards(offset, limit, searchFilter, searchText);
		return boards;
	}
	
	public Integer selectCount(int searchFilter, String searchText) {
		Integer count = boardMapper.selectCount(searchFilter, searchText);
		return count;
	}
	
	public List<BoardDto> selectByUserId(Long userId, int offset, int limit) {
		List<BoardDto> boards = boardMapper.selectByUserId(userId, offset, limit);
		return boards;
	}
	
	public Integer selectCountByUserId(Long userId) {
		Integer count = boardMapper.selectCountByUserId(userId);
		log.info(Integer.toString(count));
		return count;
	}
	
	public BoardDto selectByBoardId(Long boardId) {
		BoardDto board = boardMapper.selectByBoardId(boardId);
		return board;
	}
	
	public Long insertBoard(BoardDto board) {
		boardMapper.insertBoard(board);
		return board.getBoardId();
	}
	
	public Integer updateByBoardId(Long boardId, BoardDto board) {
		return boardMapper.updateBoard(boardId, board);
	}

	public Integer deleteBoard(Long boardId) {
		return boardMapper.deleteBoard(boardId);
	}
}