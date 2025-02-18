package com.example.community_parc.domain;

import com.example.community_parc.dto.JoinRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private EmailVerified emailVerified;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany
    @JoinColumn(name = "commentId")
    private List<Comment> comments;

    @OneToMany
    @JoinColumn(name = "postStorageId")
    private List<PostStorage> boardStorages;

    public Member(JoinRequestDTO joinRequestDTO) {
        Member.builder()
                .email(joinRequestDTO.getEmail())
                .password(joinRequestDTO.getPassword())
                .nickname(joinRequestDTO.getNickname())
                .emailVerified(EmailVerified.NotVerified)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
