package com.example.community_parc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long commentId;

    private String content;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean replyYN;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean deleteYN;


    private Long replyNUM; //답글을 단 댓글 번호

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    private Post board;
}
