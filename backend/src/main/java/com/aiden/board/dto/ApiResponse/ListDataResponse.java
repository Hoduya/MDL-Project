package com.aiden.board.dto.ApiResponse;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListDataResponse<T> extends CommonResponse {
    private List<T> data;
}
