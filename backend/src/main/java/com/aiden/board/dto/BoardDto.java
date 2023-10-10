package com.aiden.board.dto;

import java.util.Date;

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
}
