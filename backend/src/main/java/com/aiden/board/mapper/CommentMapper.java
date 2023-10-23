package com.aiden.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.board.CommentDto;

@Mapper
public interface CommentMapper {
	
	List<CommentDto> selectCommentsByBoardId(Long boardId);
	CommentDto selectCommentByCommentId(Long commentId);
	Integer insertComment(CommentDto comment);
	Integer deleteComment(Long BoardId, Long commentId);
}