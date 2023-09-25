package com.aiden.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aiden.board.dto.BoardDto;
import com.aiden.board.mapper.BoardSelectType;
import com.aiden.board.service.BoardService;
import com.aiden.board.service.ResponseService;
import com.aiden.board.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins="http://localhost:8080")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/boards")
	public Map<String, Object> getBoardList(@RequestParam int offset,
										@RequestParam(defaultValue = "10") int limit) {
		List<BoardDto> boards = boardService.selectBoards(offset, limit);
		Integer count = boardService.selectCount();
		
	    Map<String, Object> response = new HashMap<>();
	    response.put("boards", boards);
	    response.put("boardsCount", count);
	    
		return response;
	}
	
	@GetMapping("/boards/{unsername}")
	public List<BoardDto> getBoardListByUserName(@PathVariable("unsername") String username) {
		List<BoardDto> boards = boardService.selectByWriterId(unsername);
		return boards;
	}
	
	@GetMapping("/boards/{bno}")
	public BoardDto getBoardByBno(@PathVariable("id") String id) {
		BoardDto board = boardService.selectById(id);
		return board;
	}
	
	@PostMapping("/boards")
	public BoardDto createBoard(@RequestBody BoardDto board) {
		log.info(board.toString());
		return new BoardDto();//boardService.insertBoard(board);
	}
	
	@DeleteMapping("/boards")
	public void deleteBoard(@RequestParam("id") String id) {
		log.info("Delte: Delete a Board {id}", id);
		int result = boardService.deleteBoard(id);
	}
}