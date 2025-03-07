package com.example.community_parc.dto;

import com.example.community_parc.domain.AuthorType;
import com.example.community_parc.domain.Member;
import com.example.community_parc.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class GetPostDetailsResponseDTO {
    private String title;
    private String content;
    private int views;
    private int likes;
    private String author;
    private AuthorType authorType;
    private String clientIp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    private Member member;


    public static GetPostDetailsResponseDTO fromPost(Post post) {
        return GetPostDetailsResponseDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .views(post.getViews())
                .likes(post.getLikes())
                .author(post.getAuthor())
                .authorType(post.getAuthorType())
                .clientIp(post.getClientIp())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .member(post.getMember()) // Post에서 member 가져오기
                .build();
    }

}
