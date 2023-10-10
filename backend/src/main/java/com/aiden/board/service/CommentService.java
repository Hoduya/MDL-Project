package com.aiden.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aiden.board.controller.CommentController;
import com.aiden.board.dto.CommentDto;
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
		Integer result = commentMapper.insertComment(comment);
		log.info(comment.getCommentId().toString());
		return comment;
	}
	
	public void deleteComment(Long boardId, Long commentId) throws Exception {
		Integer result = commentMapper.deleteComment(boardId, commentId);
		if (result == 0) {
			throw new Exception("댓글 삭제 오류");
		}
	}
}
