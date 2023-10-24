package com.aiden.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiden.board.dto.board.CommentDto;
import com.aiden.board.dto.user.DepartmentDto;
import com.aiden.board.dto.user.UserDto;
import com.aiden.board.service.CommentService;
import com.aiden.board.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DepartmentController {

	private final DepartmentService departmentService;

	@GetMapping("/departments")
	public ResponseEntity<List<DepartmentDto>> getDepartments() {
		
		List<DepartmentDto> departments = departmentService.selectDepartments();
		return ResponseEntity.ok(departments);
	}
}