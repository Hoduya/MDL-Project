package com.aiden.board.dto.sign;

import com.aiden.board.dto.token.TokenDto;
import com.aiden.board.dto.user.UserDto;

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
