package com.aiden.board.dto.sign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpRequestDto {
    private String email;
    private String password;
    private String name;
    private Integer deptId;
}
