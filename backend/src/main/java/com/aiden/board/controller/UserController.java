package com.aiden.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.aiden.board.dto.ApiResponse.CommonResponse;
import com.aiden.board.dto.ApiResponse.ListDataResponse;
import com.aiden.board.dto.ApiResponse.SingleDataResponse;
import com.aiden.board.dto.user.ProfileDto;
import com.aiden.board.dto.user.UserDto;
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
    public SingleDataResponse<UserDto> findUserById(@PathVariable(value="userId") Long userId) {
    	
    	UserDto findUser = userService.findByUserId(userId);
        return responseService.getSingleDataResponse(findUser);
    }
    
    @PutMapping("/users")
    public SingleDataResponse<UserDto> update(@RequestBody UserDto user) {
    	
    	UserDto updateUser = userService.updateUser(user);
    	return responseService.getSingleDataResponse(updateUser);
    } 
    
    @DeleteMapping("/users")
    public CommonResponse delete(@RequestBody Long userId) {
    	
    	userService.deleteUser(userId);
    	return responseService.getSuccessResponse();
    }
    
    @GetMapping("/profiles/{deptId}")
    public ListDataResponse<ProfileDto> getProfilesByDepartmentId(@PathVariable(value="deptId") Long deptId) {
    	
    	List<ProfileDto> profiles = userService.selectProfilesFromDepartment(deptId);
    	return responseService.getListDataResponse(profiles);
    }
}
