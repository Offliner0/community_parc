package com.example.community_parc.service;

import com.example.community_parc.domain.Member;
import com.example.community_parc.dto.CustomUserDetails;
import com.example.community_parc.dto.JoinRequestDTO;
import com.example.community_parc.dto.PwResetDTO;
import com.example.community_parc.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
//@Transactional
public class AuthService {

    final MemberRepository memberRepository;
    final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //이메일 중복 확인
    public Boolean existemail(String email) {
        return memberRepository.existsByEmail(email);
    }

    //닉네임 중복 확인
    public Boolean existusername(String username) { return memberRepository.existsByNickname(username);}

    //비밀번호 재설정
    public boolean pwReset(String email ,@RequestBody PwResetDTO pwResetDTO) {

        Member member = memberRepository.findByEmail(email);

        if (member == null) throw new IllegalArgumentException("존재하지 않는 이메일입니다.");

        if (passwordEncoder.matches(pwResetDTO.getPw(), member.getPassword())) { //비밀번호 검증
            member.setPassword(passwordEncoder.encode(pwResetDTO.getNewPW()));
            memberRepository.save(member);
            return true;
        }

        return false;
    }

//    //로그인 메서드
//    public Boolean login(String email, String password) {
//        Member member = memberRepository.findByEmail(email);
//
//        return member != null && passwordEncoder.matches(password, member.getPassword());
//    }

//    public void logout(HttpSession session) {
//        session.invalidate();
//    }

    //회원가입 메서드
    public void join(JoinRequestDTO joinRequestDTO) {

        boolean existemail = existemail(joinRequestDTO.getEmail());
        if (existemail) {
            return;
        }
        memberRepository.save(joinRequestDTO.tomember(passwordEncoder));
    }
}
