package com.example.community_parc.dto;

import com.example.community_parc.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CommentDTO {

    @Getter
    @Setter
    public static class Request {

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

    @Getter
    @Setter
    @Builder
    public static class Response {
        private Long commentId;
        private String content;
        private String author;
        private String clientIP;
        private boolean deletedYN;
        private Long replyNum;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public static Response deletedComment(Comment comment) {
            return builder()
                    .deletedYN(true)
                    .replyNum(comment.getReplyNUM())
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(comment.getUpdatedAt())
                    .build();
        }

        public static Response fromComment(Comment comment) {
            return builder()
                    .commentId(comment.getCommentId())
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
}
