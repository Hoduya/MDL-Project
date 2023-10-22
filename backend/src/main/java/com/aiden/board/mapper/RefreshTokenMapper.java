package com.aiden.board.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.jwt.RefreshTokenDto;

@Mapper
public interface RefreshTokenMapper {
	
	Optional<RefreshTokenDto> findByUserId(String userId);
}
