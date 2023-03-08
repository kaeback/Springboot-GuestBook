package com.example.guestbook.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MemberDto {
    @Size(min = 4, max = 20)
    private String id;

    @Size(min = 4, max = 20)
    private String password;

    @NotBlank
    private String name;
}
