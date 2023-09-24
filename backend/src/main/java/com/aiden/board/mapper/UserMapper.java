package com.aiden.board.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.UserDto;

@Mapper
public interface UserMapper {
    Optional<UserDto> findByUserId(String userId);
    void save(UserDto userDto);
}
