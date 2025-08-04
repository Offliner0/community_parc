package com.example.community_parc.dto;

import com.example.community_parc.domain.AuthorType;
import com.example.community_parc.domain.Gallery;
import com.example.community_parc.domain.Member;
import com.example.community_parc.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.coyote.Response;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        String title;
        String content;
        String nickname = null;
        String password = null;


        public Post toPost(Member member, Gallery gallery) { //회원 게시글
            return Post.builder()
                    .id(UUID.randomUUID())
                    .title(this.getTitle())
                    .content(this.getContent())
                    .deleteYN(0)
                    .authorType(AuthorType.MEMBER)
                    .views(0)
                    .author(member.getNickname())
                    .gallery(gallery)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .member(member)
                    .build();
        }

        public Post toPost(String clientIp, Gallery gallery) { //비회원 게시글
            return Post.builder()
                    .id(UUID.randomUUID())
                    .title(this.getTitle())
                    .content(this.getContent())
                    .deleteYN(0)
                    .authorType(AuthorType.GUEST)
                    .views(0)
                    .clientIp(clientIp)
                    .gallery(gallery)
                    .author(this.getNickname())
                    .password(this.getPassword())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Response {
        String title;
        String nickname;
        String clientIP = null;
        int views;
        int likes;
        LocalDateTime createdAt;

        public static Response fromPost (Post post) {
            return Response.builder()
                    .title(post.getTitle())
                    .nickname(post.getAuthor())
                    .clientIP(post.getClientIp())
                    .views(post.getViews())
                    .likes(post.getLikes())
                    .createdAt(post.getCreatedAt())
                    .build();
        }
    }
}
