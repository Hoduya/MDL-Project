package com.aiden.board.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 
 CREATE TABLE `board` (
  `bno` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '게시글ID',
  `title` varchar(45) NOT NULL COMMENT '제목',
  `writer_name` varchar(45) NOT NULL COMMENT '작성자이름',
  `content` varchar(200) NOT NULL COMMENT '내용',
  `regDate` datetime NOT NULL COMMENT '등록일자',
  `updateDate` datetime DEFAULT NULL COMMENT '수정일자',
  PRIMARY KEY (`bno`),
  KEY `board_FK` (`writer_name`),
  CONSTRAINT `board_FK` FOREIGN KEY (`writer_name`) REFERENCES `user` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci COMMENT='게시글';

 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    Long bno;
    String writerName;
    String title;
    String content;
    Date regDate;
    Date updateDate;
}
