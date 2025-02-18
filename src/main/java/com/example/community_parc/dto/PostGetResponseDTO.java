package com.example.community_parc.dto;

import com.example.community_parc.domain.Member;
import com.example.community_parc.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostGetResponseDTO {
    private String title;
    private String content;
    private int views;
    private int likes;
    private String author;
    private int loginYN;
    private String clientIp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    private Member member;

    public PostGetResponseDTO(Post post) {
        builder()
                .title(post.getTitle())
                .content(post.getContent())
                .views(post.getViews())
                .likes(post.getLikes())
                .author(post.getAuthor())
                .loginYN(post.getLoginYN())
                .clientIp(post.getClientIp())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
