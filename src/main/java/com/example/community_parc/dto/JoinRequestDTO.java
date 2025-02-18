package com.example.community_parc.dto;

import com.example.community_parc.domain.EmailVerified;
import com.example.community_parc.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class JoinRequestDTO {
    private String email;
    private String password;
    private String nickname;

    public Member tomember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(this.getEmail())
                .password(passwordEncoder.encode(this.getPassword()))
                .nickname(this.getNickname())
                .emailVerified(EmailVerified.NotVerified)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
