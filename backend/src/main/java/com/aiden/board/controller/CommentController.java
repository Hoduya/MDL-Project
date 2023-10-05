package com.aiden.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aiden.board.dto.BoardDto;
import com.aiden.board.dto.CommentDto;
import com.aiden.board.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/boards/{bno}/comments")
	public List<CommentDto> getCommentsByBno(@PathVariable("bno") Long bno) {
		List<CommentDto> comments = commentService.selectCommentsByBno(bno);
		return comments;
	}

	@PostMapping("/boards/{bno}/comments")
	public CommentDto addComment(@PathVariable("bno") Long bno, @RequestBody Map<String, String> body) {
		String content = body.get("content");
		CommentDto comment = new CommentDto();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		comment.setBno(bno);
		comment.setContent(content);
		comment.setUsername(username);
		
		CommentDto result = commentService.insertComment(comment);
		return result;
	}
}
