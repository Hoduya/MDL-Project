package com.aiden.board.dto.MenuImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuImageDto {
	private String originName;
	private String storedName;
	private String imagePath;
}
