package com.aiden.board.dto.Token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TokenRequestDto {
	String accessToken;
	String refreshToken;
}
