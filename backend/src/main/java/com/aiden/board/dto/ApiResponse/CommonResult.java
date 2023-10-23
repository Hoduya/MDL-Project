package com.aiden.board.dto.ApiResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    private boolean success; // 응답 성공 여부: T/F
    private int code; // 응답 코드: >= 0 정상, < 0 비정상
    private String msg; // 응답 메시지
}
