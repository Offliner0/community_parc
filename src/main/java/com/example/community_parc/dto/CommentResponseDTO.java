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
public class CommentResponseDTO {
    private UUID commentId;
    private String content;
    private String author;
    private String clientIP;
    private boolean deletedYN;
    private Long replyNum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponseDTO deletedComment(Comment comment) {
        return builder()
                .deletedYN(true)
                .replyNum(comment.getReplyNUM())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public static CommentResponseDTO fromComment(Comment comment) {
        return builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .clientIP(comment.getClientIP())
                .deletedYN(false)
                .replyNum(comment.getReplyNUM())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
