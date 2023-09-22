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

import com.aiden.board.domain.BoardVO;
import com.aiden.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/boards")
	public List<BoardVO> getBoardList() {
		List<BoardVO> boards = boardService.selectAll();
		
		for(BoardVO board: boards) {
			System.out.println(board.getBoardId());
		}
		
		return boards;
	}
	
	@GetMapping("/boards-writer/{id}")
	public List<BoardVO> getBoardListByWriterId(@PathVariable("id") String id) {
		List<BoardVO> boards = boardService.selectByWriterId(id);
		return boards;
	}
	
	@GetMapping("/boards/{id}")
	public BoardVO getBoardById(@PathVariable("id") String id) {
		BoardVO board = boardService.selectById(id);
		return board;
	}
	
	@PostMapping("/boards")
	public BoardVO insertBoard(@RequestBody BoardVO board) {
		log.info(board.toString());
		return new BoardVO();//boardService.insertBoard(board);
	}
	
	@DeleteMapping("/boards")
	public void deleteBoard(@RequestParam("id") String id) {
		log.info("Delte: Delete a Board {id}", id);
		int result = boardService.deleteBoard(id);
	}
}
