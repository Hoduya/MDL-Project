package com.aiden.board.service;

import java.io.Console;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aiden.board.controller.CommentController;
import com.aiden.board.dto.board.CommentDto;
import com.aiden.board.mapper.BoardMapper;
import com.aiden.board.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentMapper commentMapper;
	
	public List<CommentDto> selectCommentsByBoardId(Long boardId) {
		List<CommentDto> comments = commentMapper.selectCommentsByBoardId(boardId);
		return comments;
	}
		
	public CommentDto insertComment(CommentDto comment) {
		commentMapper.insertComment(comment);
		CommentDto result = commentMapper.selectCommentByCommentId(comment.getCommentId());
		return result;
	}
	
	public void deleteComment(Long boardId, Long commentId) {
		Integer result = commentMapper.deleteComment(boardId, commentId);
		if (result == 0) {
			log.info("댓글 삭제 실패 ");
		}
	}
}
