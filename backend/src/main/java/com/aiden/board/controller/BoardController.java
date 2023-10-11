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
	public Map<String, Object> getBoardList(@RequestParam(value="authorId", required=false) Long userId, 
											@RequestParam int offset, 
											@RequestParam(defaultValue = "10") int limit) {
		List<BoardDto> boards;
		Integer count;
		
		if (userId == null) {
			boards = boardService.selectBoards(offset, limit);
			count = boardService.selectCount();
		} else {
			boards = boardService.selectByUserId(userId, offset, limit);
			count = boardService.selectCountByUserId(userId);
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put("boards", boards);
		response.put("boardsCount", count);
		
		return response;
	}


	@GetMapping("/boards/{boardId}")
	public BoardDto getBoardByboardId(@PathVariable("boardId") Long boardId) {
		BoardDto board = boardService.selectByBoardId(boardId);
		return board;
	}

	@PostMapping("/boards")
	public BoardDto createBoard(@RequestBody BoardDto board, final Authentication authentication) {
        Long currentUserId = ((UserDto) authentication.getPrincipal()).getUserId();
		board.setRegDate(new Date());
		board.setUserId(currentUserId);
		Long boardId = boardService.insertBoard(board);
		BoardDto result = boardService.selectByBoardId(boardId);
		return result;
	}
	
	@PutMapping("/boards/{boardId}")
	public BoardDto updateBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardDto board, final Authentication authentication) {
        Long currentUserId = ((UserDto) authentication.getPrincipal()).getUserId();
		board.setUserId(currentUserId);
		boardService.updateByBoardId(boardId, board);
		BoardDto result = boardService.selectByBoardId(boardId);
		return result;
	}

	@DeleteMapping("/boards/{boardId}")
	public void deleteBoard(@PathVariable("boardId") Long boardId) {
		log.info("Delte: Delete a Board {boardId}", boardId);
		int result = boardService.deleteBoard(boardId);
	}
}