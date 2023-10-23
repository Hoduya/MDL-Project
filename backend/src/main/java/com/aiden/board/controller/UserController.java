package com.aiden.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.aiden.board.dto.ApiResponse.CommonResult;
import com.aiden.board.dto.ApiResponse.ListResult;
import com.aiden.board.dto.ApiResponse.SingleResult;
import com.aiden.board.dto.User.ProfileDto;
import com.aiden.board.dto.User.UserDto;
import com.aiden.board.service.UserService;
import com.aiden.board.service.response.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;
    
    @GetMapping("/users/{userId}")
    public SingleResult<UserDto> findUserById(@PathVariable(value="userId") Long userId) {
    	
    	UserDto findUser = userService.findByUserId(userId);
        return responseService.getSingleResult(findUser);
    }
    
    @PutMapping("/users")
    public SingleResult<UserDto> update(@RequestBody UserDto user) {
    	
    	UserDto updateUser = userService.updateUser(user);
    	return responseService.getSingleResult(updateUser);
    } 
    
    @DeleteMapping("/users")
    public CommonResult delete(@RequestBody Long userId) {
    	
    	userService.deleteUser(userId);
    	return responseService.getSuccessResult();
    }
    
    @GetMapping("/profiles/{deptId}")
    public ListResult<ProfileDto> getProfilesByDepartmentId(@PathVariable(value="deptId") Long deptId) {
    	
    	List<ProfileDto> profiles = userService.selectProfilesFromDepartment(deptId);
    	return responseService.getListResult(profiles);
    }
}
