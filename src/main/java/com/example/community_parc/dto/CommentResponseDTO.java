package com.example.community_parc.dto;

import com.example.community_parc.domain.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private Long commentId;
    private String content="test";
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

        return builder() // 빌더 패턴 사용
                .commentId(comment.getCommentId())
                .author(comment.isDeleteYN() ? null : comment.getAuthor())
                .clientIP(comment.isDeleteYN() ? null : comment.getClientIP())
                .deletedYN(comment.isDeleteYN()) // 삭제 여부 설정
                .replyNum(comment.getReplyNUM())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .content(comment.isDeleteYN() ? "삭제된 댓글입니다." : comment.getContent()) // content 설정
                .build();
    }
}
