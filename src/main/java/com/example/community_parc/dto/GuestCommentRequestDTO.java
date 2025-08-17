package com.example.community_parc.dto;

import com.example.community_parc.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class GuestCommentRequestDTO {

    private String content;

    private String author;

    private String password;


    public Comment toComment() {
        return Comment.builder()
                .content(this.content)
                .author(this.author)
                .password(this.password)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Comment toComment(UUID replyNum) {
        return Comment.builder()
                .content(this.content)
                .author(this.author)
                .password(this.password)
                .replyNUM(replyNum)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
