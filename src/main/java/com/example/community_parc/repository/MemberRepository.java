package com.example.community_parc.repository;

import com.example.community_parc.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByEmail(String email);

    Member findByEmail(String email);

    Member findByAuthenticationKey(String authenticationKey);
}
