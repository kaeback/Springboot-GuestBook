package com.example.guestbook.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GuestBook {
    private Long guestbook_id;
    private String contents;
    private String member_id;
    private int hit;
    private LocalDateTime created_time;
    private LocalDateTime lastModified_time;
}
