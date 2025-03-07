package com.example.community_parc.controller;

import com.example.community_parc.dto.CustomUserDetails;
import com.example.community_parc.dto.JoinRequestDTO;
import com.example.community_parc.dto.PwResetDTO;
import com.example.community_parc.jwt.JwtUtil;
import com.example.community_parc.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(value = "/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @GetMapping("/loginview") //로그인 뷰페이지
    public String login() {
        return "login.html";
    }


    //비밀번호 재설정
    @PostMapping("/pwreset")
    public ResponseEntity<Void> pwReset(@RequestBody PwResetDTO pwResetDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            String email = customUserDetails.getUsername();
            authService.pwReset(email, pwResetDTO);

            return ResponseEntity.ok().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok().build();
    }

    //회원가입 뷰페이지
    @GetMapping("/join")
    public String join() {
        return "join.html";
    }

    //회원가입
    @PostMapping("/joinProc")
    public ResponseEntity<String> joinProcess(@RequestBody JoinRequestDTO joinRequestDTO) {

        authService.join(joinRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
    }



}
