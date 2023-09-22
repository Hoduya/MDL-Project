package com.aiden.board.domain;

import java.util.Date;

import lombok.Data;
import lombok.Getter;

@Data
public class BoardVO {
	
	private Integer boardId;
	private String id;
	private String title;
	private String content;
	private Date regDate;
	private Date updateDate;
}
