package com.aiden.board.dto.login;

import com.aiden.board.dto.Token.TokenDto;
import com.aiden.board.dto.User.UserDto;

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
public class LoginResponseDto {
	private TokenDto token;
	private UserDto user;
}
