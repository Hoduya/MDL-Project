package com.aiden.board.dto.board;

import java.util.Date;

import com.aiden.board.dto.user.ProfileDto;
import com.aiden.board.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    Long boardId;
    Long userId;
    String title;
    String content;
    Date regDate;
    Date updateDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private ProfileDto author;
}

