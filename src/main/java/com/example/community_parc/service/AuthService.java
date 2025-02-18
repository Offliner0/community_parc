package com.example.community_parc.service;

import com.example.community_parc.domain.Member;
import com.example.community_parc.dto.JoinRequestDTO;
import com.example.community_parc.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
//@Transactional
public class AuthService {

    MemberRepository memberRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //이메일 확인 메서드
    public Boolean existemail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public Boolean existusername(String username) { return memberRepository.existsByNickname(username);}

    //로그인 메서드
    public Boolean login(String email, String password) {
        Member member = memberRepository.findByEmail(email);

        return member != null && passwordEncoder.matches(password, member.getPassword());
    }

//    public void logout(HttpSession session) {
//        session.invalidate();
//    }

    //회원가입 메서드
    public void join(JoinRequestDTO joinRequestDTO) {

        memberRepository.save(joinRequestDTO.tomember(passwordEncoder));
    }
}
