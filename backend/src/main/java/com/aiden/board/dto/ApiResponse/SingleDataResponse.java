package com.aiden.board.dto.ApiResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleDataResponse<T> extends CommonResponse {
    private T data;
}
