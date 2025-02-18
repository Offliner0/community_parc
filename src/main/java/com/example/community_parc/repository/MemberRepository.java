package com.example.community_parc.repository;

import com.example.community_parc.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    boolean existsByEmail(String email);

    Member findByEmail(String email);

    boolean existsByNickname(String username);


}
