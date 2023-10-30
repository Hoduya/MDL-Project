package com.aiden.board.dto.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDto {
	private Long componentId;
	private Long userId;
	private Integer deptId;
	private String userName;
	private Integer coordX;
	private Integer coordY;
	private Integer voteState;
}
