package com.aiden.board.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.aiden.board.dto.User.ProfileDto;
import com.aiden.board.dto.User.UserDto;

@Mapper
public interface UserMapper {
    Optional<UserDto> findByUserEmail(String email);
    Optional<UserDto> findByUserName(String username);
    Optional<UserDto> findByUserId(Long userId);
	List<ProfileDto> selectProfilesFromDepartment(Long deptId);
    Integer updateUser(UserDto user);
    void save(UserDto userDto);
	Integer deleteUser(Long userId);
}
