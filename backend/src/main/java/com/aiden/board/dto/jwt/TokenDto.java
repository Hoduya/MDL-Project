package com.aiden.board.dto.jwt;

import java.util.Date;

import lombok.Builder;

@Builder
public class TokenDto {
	private String grantType;
	private String accessToken;
	private String refreshToken;
	private Date accessTokenExpireDate;
}
