package com.example.community_parc.service;

import com.example.community_parc.domain.Member;
import com.example.community_parc.dto.CustomUserDetails;
import com.example.community_parc.dto.JoinRequestDTO;
import com.example.community_parc.dto.PwResetDTO;
import com.example.community_parc.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.standard.expression.MessageExpression;

import java.util.UUID;


@Service
//@Transactional
public class AuthService {

    final MemberRepository memberRepository;
    final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Autowired
    public AuthService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

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

    //회원가입 메서드
    public void join(JoinRequestDTO joinRequestDTO) {

        if (memberRepository.existsByEmail(joinRequestDTO.getEmail())) {
            return;
        }

        Member member = joinRequestDTO.tomember(passwordEncoder);

        String key = UUID.randomUUID().toString();
        member.setAuthenticationKey(key);

        System.out.println(key);

        sendAuthenticationEmail(joinRequestDTO.getEmail(),key);

        memberRepository.save(joinRequestDTO.tomember(passwordEncoder));
    }

    //회원가입 인증 이메일 송신
    private void sendAuthenticationEmail(String email,String key){

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(""); //보낼사람
            message.setRecipients(MimeMessage.RecipientType.TO,email);
            message.setSubject("communityPrac 인증 이메일");
            String body = "<href> localhost::8080/emailAuth/" + key + "</href>";
            message.setText(body,"UTF-8","html");

        }catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //이메일 인증
    public boolean emailAuthentication(String key){
        Member member = memberRepository.findByAuthenticationKey(key);
        if (member != null) {
            member.setAuthenticationKey(null); //인증 후 키 값 null
            memberRepository.save(member);
            return true;
        }
        return false;
    }
}
