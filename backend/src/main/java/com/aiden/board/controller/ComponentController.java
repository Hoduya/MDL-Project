package com.aiden.board.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiden.board.dto.component.ComponentDto;
import com.aiden.board.dto.component.UpdateComponentDto;
import com.aiden.board.dto.user.DepartmentDto;
import com.aiden.board.service.ComponentService;
import com.aiden.board.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ComponentController {
	
	private final ComponentService componentService;

	@GetMapping("/components/{deptId}")
	public ResponseEntity<List<ComponentDto>> getComponentsByDeptId(@PathVariable("deptId") Integer deptId) {
		
		List<ComponentDto> components = componentService.selectByDeptId(deptId);
		return ResponseEntity.ok(components);
	}
	
	@PutMapping("/components")
	public ResponseEntity<Void> updatePosition(@RequestBody UpdateComponentDto component) {
		log.info(component.toString());
		componentService.updatePosition(component);
		return ResponseEntity.ok().build();
	}
}
