package com.aiden.board.dto.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {

    private String status; 
    private String message; // 응답 메시지
}
