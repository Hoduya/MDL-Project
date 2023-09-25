package com.aiden.board.service;


import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiden.board.dto.BoardDto;
import com.aiden.board.mapper.BoardMapper;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private static final Logger log = LoggerFactory.getLogger(BoardService.class);
	
	private final BoardMapper boardMapper;
	
	public List<BoardDto> selectBoards(int offset, int limit) {
		List<BoardDto> boards = boardMapper.selectBoards(offset, limit);
		return boards;
	}
	
	public List<BoardDto> selectByUserName(String username) {
		List<BoardDto> boards = boardMapper.selectByUserName(username);
		return boards;
	}
	
	public Integer selectCount() {
		Integer count = boardMapper.selectCount();
		return count;
	}
	
	public Integer selectCountByUserName(String username) {
		Integer count = boardMapper.selectCount(username);
		return count;
	}
	
	public BoardDto selectByBno(Long bno) {
		BoardDto board = boardMapper.selectByBno(bno);
		return board;
	}
	
	public Long insertBoard(BoardDto board) {
		boardMapper.insertBoard(board);
		return board.getBno();
	}

	public Integer deleteBoard(String id) {
		return boardMapper.deleteBoard(id);
	}
}