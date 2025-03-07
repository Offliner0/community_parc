package com.example.community_parc.dto;

import com.example.community_parc.domain.AuthorType;
import com.example.community_parc.domain.Gallery;
import com.example.community_parc.domain.Member;
import com.example.community_parc.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PostRequestDTO {

    String title;
    String content;
    String nickname = null;
    String password = null;


    public Post toPost(Member member, Gallery gallery) { //회원 게시글
        return Post.builder()
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
