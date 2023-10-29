package com.aiden.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.aiden.board.dto.user.ProfileDto;
import com.aiden.board.dto.user.UserDto;
import com.aiden.board.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable(value="userId") Long userId) {
    	
    	UserDto findUser = userService.findByUserId(userId);
        return ResponseEntity.ok(findUser);
    }
    
    @PutMapping("/users")
    public ResponseEntity<UserDto> update(@RequestBody UserDto user) {
    	
    	UserDto updateUser = userService.updateUser(user);
    	return ResponseEntity.ok(updateUser);
    } 
    
    @DeleteMapping("/users")
    public ResponseEntity<Void> delete(final Authentication authentication) {
    	
    	userService.deleteUser(Long.parseLong(authentication.getName()));
    	return ResponseEntity.ok().build();
    }
    
    @GetMapping("/profiles/{deptId}")
    public ResponseEntity<List<ProfileDto>> getProfilesByDepartmentId(@PathVariable(value="deptId") Long deptId) {
    	
    	List<ProfileDto> profiles = userService.selectProfilesFromDepartment(deptId);
    	return ResponseEntity.ok(profiles);
    }
}
