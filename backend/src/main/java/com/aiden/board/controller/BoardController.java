package com.aiden.board.controller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aiden.board.dto.BoardDto;
import com.aiden.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/boards")
	public List<BoardDto> getBoardList(@RequestParam int offset,
										@RequestParam(defaultValue = "10") int limit) {
		List<BoardDto> boards = boardService.selectBoards(offset, limit);
		
		log.info(boards.toString());
		
		return boards;
	}
	
	@GetMapping("/boards-writer/{id}")
	public List<BoardDto> getBoardListByWriterId(@PathVariable("id") String id) {
		List<BoardDto> boards = boardService.selectByWriterId(id);
		return boards;
	}
	
	@GetMapping("/boards/{bno}")
	public BoardDto getBoardById(@PathVariable("id") String id) {
		BoardDto board = boardService.selectById(id);
		return board;
	}
	
	@PostMapping("/boards")
	public BoardDto insertBoard(@RequestBody BoardDto board) {
		log.info(board.toString());
		return new BoardDto();//boardService.insertBoard(board);
	}
	
	@DeleteMapping("/boards")
	public void deleteBoard(@RequestParam("id") String id) {
		log.info("Delte: Delete a Board {id}", id);
		int result = boardService.deleteBoard(id);
	}
}