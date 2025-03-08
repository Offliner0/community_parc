package com.example.community_parc.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyPageDTO {
    String nickname;
    LocalDateTime createdAt;
    Page<PostDTO.Response> myPosts;
    Page<CommentDTO.Response> myComments;
}