package com.example.community_parc.controller;

import com.example.community_parc.domain.Member;
import com.example.community_parc.dto.JoinRequestDTO;
import com.example.community_parc.dto.LoginRequestDTO;
import com.example.community_parc.service.AuthService;
import com.example.community_parc.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final RestClient.Builder builder;
    AuthService authService;

    public AuthController(RestClient.Builder builder) {
        this.builder = builder;
    }

    @GetMapping("/login") //로그인 뷰페이지
    public String longin() {
        return "login.html";
    }

    @PostMapping("/loginProc") //로그인 요청
    public String login(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session, HttpServletRequest request) {
       boolean success = authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        if (success) {
            session.invalidate(); // 이전 세션 무효화
            session = request.getSession(true); // 새로운 세션 생성
            session.setAttribute("email", loginRequestDTO.getEmail());
            return "로그인 성공";
        }
        return "잘못된 이메일이나 비밀번호 입니다.";
    }

//    @PostMapping("/pwreset")
//    public String pwReset(HttpSession session) {
//
//    }


    @GetMapping //로그아웃
    public ResponseEntity logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok().build();
    }


    @GetMapping("/emailexistcheck/{email}") //이메일 중복확인
    public String emailexistcheck(@PathVariable String email) { //return 타입을 바꿔야할 듯

        if (authService.existemail(email)){
            return "사용할 수 없는 이메일입니다.";
        }else return "사용 가능한 이메일입니다.";

    }

    @GetMapping("/nicknameexistcheck/{nickname}") //닉네임 중복 확인
    public String nicknameexistcheck(@PathVariable String nickname) {
        if (authService.existusername(nickname)){
            return "사용할 수 없는 닉네임입니다.";
        }else return "사용 가능한 닉네임입니다.";
    }

    @GetMapping("/join")//회원가입 뷰페이지
    public String join() {
        return "join.html";
    }

    @PostMapping("/joinProc") //회원가입
    public ResponseEntity<String> join(@RequestBody JoinRequestDTO joinRequestDTO) {

        if (authService.existemail(joinRequestDTO.getEmail())){ //이메일 중복 확인
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일이 이미 존재합니다.");
        }

        authService.join(joinRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
    }



}
