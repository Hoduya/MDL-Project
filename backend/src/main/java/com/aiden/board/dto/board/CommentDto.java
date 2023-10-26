package com.aiden.board.dto.board;

import java.util.Date;

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
public class CommentDto {
	private Long commentId;
	private Long userId;
	private Long boardId;
	private String content;
	private Date regDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UserDto author;
}
