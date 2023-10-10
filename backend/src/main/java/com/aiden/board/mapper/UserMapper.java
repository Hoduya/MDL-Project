package com.aiden.board.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.UserDto;

@Mapper
public interface UserMapper {
    Optional<UserDto> findByUserEmail(String email);
    Optional<UserDto> findByUserName(String username);
    Optional<UserDto> findByUserId(Long userId);
    void save(UserDto userDto);
}
