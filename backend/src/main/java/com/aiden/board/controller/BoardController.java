package com.aiden.board.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aiden.board.dto.BoardDto;
import com.aiden.board.dto.UserDto;
import com.aiden.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:8080")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

	private final BoardService boardService;

	@GetMapping("/boards")
	public Map<String, Object> getBoardList(@RequestParam(value="author", required=false) String username, 
											@RequestParam int offset, 
											@RequestParam(defaultValue = "10") int limit) {
		List<BoardDto> boards;
		Integer count;
		
		if (username == null) {
			boards = boardService.selectBoards(offset, limit);
			count = boardService.selectCount();
		} else {
			boards = boardService.selectByUserName(username, offset, limit);
			count = boardService.selectCountByUserName(username);
			log.info(Integer.toString(count));
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put("boards", boards);
		response.put("boardsCount", count);
		
		return response;
	}


	@GetMapping("/boards/{bno}")
	public BoardDto getBoardByBno(@PathVariable("bno") String bno) {
		BoardDto board = boardService.selectByBno(Long.parseLong(bno));
		return board;
	}

	@PostMapping("/boards")
	public BoardDto createBoard(@RequestBody BoardDto board) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		board.setRegDate(new Date());
		board.setWriterName(username);
		Long bno = boardService.insertBoard(board);
		BoardDto result = boardService.selectByBno(bno);
		return result;
	}
	
	@PutMapping("/boards/{bno}")
	public BoardDto updateBoard(@PathVariable("bno") String bno, @RequestBody BoardDto board) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		board.setRegDate(new Date());
		board.setWriterName(username);
		boardService.updateByBoard(bno, board);
		BoardDto result = boardService.selectByBno(Long.valueOf(bno));
		return result;
	}

	@DeleteMapping("/boards/{bno}")
	public void deleteBoard(@PathVariable("bno") String bno) {
		log.info("Delte: Delete a Board {bno}", bno);
		int result = boardService.deleteBoard(bno);
	}
}