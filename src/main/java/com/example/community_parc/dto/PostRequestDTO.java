package com.example.community_parc.dto;

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
    String image;


    public Post toPost(Member member, Gallery gallery) { //회원 게시글
        return Post.builder()
                .title(this.getTitle())
                .content(this.getContent())
                .deleteYN(0)
                .loginYN(1)
                .views(0)
                .author(member.getNickname())
                .gallery(gallery)
                .member(member)
                .build();
    }

    public Post toPost(String clientIp, Gallery gallery) { //비회원 게시글
        return Post.builder()
                .title(this.getTitle())
                .content(this.getContent())
                .deleteYN(0)
                .loginYN(0)
                .views(0)
                .clientIp(clientIp)
                .gallery(gallery)
                .author(this.getNickname())
                .password(this.getPassword())
                .build();
    }
}
