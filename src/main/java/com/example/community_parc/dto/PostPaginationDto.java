package com.example.community_parc.dto;

import com.example.community_parc.domain.AuthorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PostPaginationDto {

    private String title;
    private String nickname;
    private AuthorType authorType;
    private String clientIp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likes;

}
