package com.example.community_parc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@AllArgsConstructor
public class CommentPainationDto {
    private String content;
    private String author;
    private String clientIP;
    private boolean replyYN;
    private UUID replyNum;
    private boolean deleteYN;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
