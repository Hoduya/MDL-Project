package com.aiden.board.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.token.RefreshTokenDto;

@Mapper
public interface RefreshTokenMapper {
	void createTuple(Long userId);
	Optional<RefreshTokenDto> findByUserId(Long userId);
	void write(RefreshTokenDto refreshToken);
}
