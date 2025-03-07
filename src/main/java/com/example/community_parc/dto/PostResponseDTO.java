package com.example.community_parc.dto;

import com.example.community_parc.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostResponseDTO {
    String title;
    String nickname;
    String clientIP = null;
    int views;
    int likes;
    LocalDateTime createdAt;

//    public PostResponseDTO (Post post) {
//        PostResponseDTO.builder()
//                .title(post.getTitle())
//                .nickname(post.getAuthor())
//                .clientIP(post.getClientIp())
//                .views(post.getViews())
//                .likes(post.getLikes())
//                .createdAt(post.getCreatedAt())
//                .build();
//    }

    public static PostResponseDTO fromPost (Post post) {
        return PostResponseDTO.builder()
                .title(post.getTitle())
                .nickname(post.getAuthor())
                .clientIP(post.getClientIp())
                .views(post.getViews())
                .likes(post.getLikes())
                .createdAt(post.getCreatedAt())
                .build();
    }
}


