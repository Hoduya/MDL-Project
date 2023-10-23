package com.aiden.board.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import com.aiden.board.dto.Token.RefreshTokenDto;

@Mapper
public interface RefreshTokenMapper {
	Optional<RefreshTokenDto> findByKey(Long userId);
	void save(RefreshTokenDto refreshToken);
}
