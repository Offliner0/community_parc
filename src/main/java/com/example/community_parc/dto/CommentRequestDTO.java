package com.example.community_parc.dto;

import com.example.community_parc.domain.Comment;
import com.example.community_parc.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentRequestDTO {

    private String content;
    private String password;

    public Comment toComment() {
        return Comment.builder()
                .content(this.content)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Comment toComment(Long replyNum) {
        return Comment.builder()
                .content(this.content)
                .replyNUM(replyNum)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
