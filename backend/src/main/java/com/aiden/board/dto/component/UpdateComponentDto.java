package com.aiden.board.dto.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateComponentDto {
	private Long componentId;
	private Integer coordX;
	private Integer coordY;
}
