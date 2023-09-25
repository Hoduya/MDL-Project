package com.aiden.board.service;

import java.util.Date;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aiden.board.dto.BoardDto;
import com.aiden.board.mapper.BoardMapper;
import com.aiden.board.mapper.BoardSelectType;
import com.aiden.board.mapper.UserMapper;
import com.aiden.board.utils.JwtTokenProvider;
import com.fasterxml.jackson.databind.deser.impl.BeanAsArrayBuilderDeserializer;

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
	
	public Integer selectCount() {
		Integer count = boardMapper.selectCount();
		return count;
	}
	
	public List<BoardDto> selectByUserName(String username) {
		List<BoardDto> boards = boardMapper.selectByWriterId(username);
		return boards;
	}
	
	public BoardDto selectById(String id) {
		BoardDto board = boardMapper.selectById(id);
		return board;
	}
	
	public BoardDto insertBoard(BoardDto board) {
		board.setRegDate(new Date());
		boardMapper.insertBoard(board);
		return board;
	}
	
	public Integer deleteBoard(String id) {
		return boardMapper.deleteBoard(id);
	}
}