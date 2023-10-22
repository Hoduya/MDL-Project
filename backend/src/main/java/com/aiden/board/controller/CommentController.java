package com.aiden.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aiden.board.dto.User.UserDto;
import com.aiden.board.dto.board.BoardDto;
import com.aiden.board.dto.board.CommentDto;
import com.aiden.board.service.CommentService;
import com.aiden.board.service.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

	private final CommentService commentService;
	private final ResponseService responseService;

	@GetMapping("/boards/{boardId}/comments")
	public List<CommentDto> getCommentsByBno(@PathVariable("boardId") Long boardId) {
		List<CommentDto> comments = commentService.selectCommentsByBoardId(boardId);
		return comments;
	}

	@PostMapping("/boards/{boardId}/comments")
	public CommentDto addComment(@PathVariable("boardId") Long boardId, @RequestBody Map<String, String> body,
			final Authentication authentication) {
		String content = body.get("content");
		CommentDto comment = new CommentDto();
		Long currentUserId = ((UserDto) authentication.getPrincipal()).getUserId();
		comment.setBoardId(boardId);
		comment.setContent(content);
		comment.setUserId(currentUserId);

		CommentDto result = commentService.insertComment(comment);
		log.info(result.getAuthor().getEmail());
		return result;
	}

	@DeleteMapping("/boards/{boardId}/comments/{commentId}")
	public void deleteComment(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId) {

		commentService.deleteComment(boardId, commentId);
	}
}
