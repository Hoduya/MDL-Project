package com.aiden.board.service;

import java.util.Date;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiden.board.domain.BoardVO;
import com.aiden.board.mapper.BoardMapper;
import com.fasterxml.jackson.databind.deser.impl.BeanAsArrayBuilderDeserializer;

@Service
public class BoardService {
	
	private static final Logger log = LoggerFactory.getLogger(BoardService.class);
	
	@Autowired
	private final BoardMapper boardMapper;
	
	public BoardService(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}
	
	public List<BoardVO> selectAll() {
		List<BoardVO> boards = boardMapper.selectAll();
		return boards;
	}
	
	public List<BoardVO> selectByWriterId(String writerId) {
		List<BoardVO> boards = boardMapper.selectByWriterId(writerId);
		return boards;
	}
	
	public BoardVO selectById(String id) {
		BoardVO board = boardMapper.selectById(id);
		return board;
	}
	
	public BoardVO insertBoard(BoardVO board) {
		board.setRegDate(new Date());
		boardMapper.insertBoard(board);
		return board;
	}
	
	public Integer deleteBoard(String id) {
		return boardMapper.deleteBoard(id);
	}
}
