package com.aiden.board.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
CREATE TABLE `comment` (
		  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '댓글ID',
		  `bno` bigint(20) NOT NULL COMMENT '작성자',
		  `content` varchar(45) NOT NULL COMMENT '내용',
		  `username` varchar(45) NOT NULL,
		  `regDate` datetime NOT NULL COMMENT '등록일자',
		  PRIMARY KEY (`comment_id`),
		  KEY `fk_comment_board` (`bno`),
		  CONSTRAINT `fk_comment_board` FOREIGN KEY (`bno`) REFERENCES `board` (`bno`) ON DELETE CASCADE ON UPDATE CASCADE
		) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci COMMENT='게시글 댓글';
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	private Long commentId;
	private Long userId;
	private Long boardId;
	private String content;
	private Date regDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UserDto author;
}
