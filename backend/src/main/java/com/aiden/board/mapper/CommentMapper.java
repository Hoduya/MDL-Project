package com.aiden.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.CommentDto;

@Mapper
public interface CommentMapper {
	public List<CommentDto> selectCommentsByBoardId(Long BoardId);
	public Integer insertComment(CommentDto comment);
	public Integer deleteComment(Long BoardId, Long commentId);
}