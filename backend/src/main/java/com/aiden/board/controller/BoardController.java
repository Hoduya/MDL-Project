package com.aiden.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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


import com.aiden.board.dto.board.BoardDto;
import com.aiden.board.dto.user.UserDto;
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
	public ResponseEntity<Map<String, Object>> getBoardList(@RequestParam int offset, 
											@RequestParam(defaultValue = "10") int limit,
											@RequestParam(value="authorId", required=false) Long userId,
											@RequestParam(value="searchFilter", defaultValue = "0") int searchFilter,
											@RequestParam(value="searchText", defaultValue = "") String searchText) {
		List<BoardDto> boards;
		Integer count;
		
		if (userId == null) {
			boards = boardService.selectBoards(offset, limit, searchFilter, searchText);
			count = boardService.selectCount(searchFilter, searchText);
		} else {
			boards = boardService.selectByUserId(userId, offset, limit);
			count = boardService.selectCountByUserId(userId);
		}

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("boards", boards);
		responseData.put("boardsCount", count);

		return ResponseEntity.ok(responseData);
	}

	@GetMapping("/boards/{boardId}")
	public ResponseEntity<BoardDto> getBoardByboardId(@PathVariable("boardId") Long boardId) {
		
		BoardDto board = boardService.selectByBoardId(boardId);
		return ResponseEntity.ok(board);
	}

	@PostMapping("/boards")
	public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto board, final Authentication authentication) {
				
		Long currentUserId = ((UserDto) authentication.getPrincipal()).getUserId();
		board.setUserId(currentUserId);
		Long boardId = boardService.insertBoard(board);
		BoardDto createdBoard = boardService.selectByBoardId(boardId);
		
		return ResponseEntity.ok(createdBoard);
	}

	@PutMapping("/boards/{boardId}")
	public ResponseEntity<BoardDto> updateBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardDto board,
			final Authentication authentication) {
		
		Long currentUserId = ((UserDto) authentication.getPrincipal()).getUserId();
		board.setUserId(currentUserId);
		boardService.updateByBoardId(boardId, board);
		BoardDto updatedBoard = boardService.selectByBoardId(boardId);
		
		return ResponseEntity.ok(updatedBoard);
	}

	@DeleteMapping("/boards/{boardId}")
	public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") Long boardId) {
		boardService.deleteBoard(boardId);
		return ResponseEntity.ok().build();
	}
}