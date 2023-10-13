package com.aiden.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiden.board.dto.CommentDto;
import com.aiden.board.dto.UserDto;
import com.aiden.board.dto.response.BaseResponse;
import com.aiden.board.service.CommentService;
import com.aiden.board.service.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DepartmentController {

	private final CommentService commentService;
    private final ResponseService responseService;

	@GetMapping("/boards/{boardId}/comments")
	public List<CommentDto> getCommentsByBno(@PathVariable("boardId") Long boardId) {
		List<CommentDto> comments = commentService.selectCommentsByBoardId(boardId);
		return comments;
	}
}