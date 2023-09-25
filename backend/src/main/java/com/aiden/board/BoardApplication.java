package com.aiden.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}


}

/*

-- board_project.board definition

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



-- board_project.comment definition

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


-- board_project.`user` definition

CREATE TABLE `user` (
  `name` varchar(45) NOT NULL COMMENT '이름',
  `id` varchar(45) NOT NULL COMMENT '이메일',
  `password` varchar(100) NOT NULL COMMENT '비밀번호',
  `role` varchar(45) DEFAULT NULL COMMENT '권한',
  `regDate` datetime NOT NULL COMMENT '가입일자',
  PRIMARY KEY (`name`),
  UNIQUE KEY `unique_name` (`name`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci COMMENT='회원';

*/