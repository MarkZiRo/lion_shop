package com.example.shopping2.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberSignupResponseDto {

    private String password;
    private String id;
    private LocalDateTime date;
}
