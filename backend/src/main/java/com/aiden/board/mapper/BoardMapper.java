package com.aiden.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.domain.BoardVO;

@Mapper
public interface BoardMapper {
	
	public List<BoardVO> selectAll();
	public List<BoardVO> selectByWriterId(String writerId);
	public BoardVO selectById(String id);
	public Integer insertBoard(BoardVO board);
	public Integer deleteBoard(String id);
}
