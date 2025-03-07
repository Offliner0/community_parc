package com.example.community_parc.service;

import com.example.community_parc.domain.Member;
import com.example.community_parc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    MemberRepository memberRepository;

    public Member getMemberById(String nickname) {
        return memberRepository.findByNickname(nickname);
    }


}
