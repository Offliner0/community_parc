package com.example.community_parc.controller;

import com.example.community_parc.domain.Post;
import com.example.community_parc.dto.PostGetResponseDTO;
import com.example.community_parc.dto.PostRequestDTO;
import com.example.community_parc.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postservice;

    @GetMapping("/{gallery}/post/{postNum}")
    public ResponseEntity<PostGetResponseDTO> post(@PathVariable String gallery,@PathVariable Long postSeq, HttpServletRequest request) {
        PostGetResponseDTO post = postservice.getPost(postSeq,gallery);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{gallery}/postProc/") // 게시글 등록
    public ResponseEntity<Void> postProc(@PathVariable String gallery,
                                         @RequestBody PostRequestDTO postRequestDTO,
                                         HttpSession httpSession,
                                         HttpServletRequest request) {
        try {
            String email = (String) httpSession.getAttribute("email");
            String clientIp = request.getHeader("X-Forwarded-For");

            if (email == null) { // 비회원인 경우
                postservice.setUnknownPost(postRequestDTO, clientIp, gallery);
            } else { // 회원인 경우
                postservice.setMemberPost(postRequestDTO, email, gallery);
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 예외 처리 (로그 기록, 사용자에게 알림 등)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 실패 시 500 상태 코드 반환
        }
    }

    @GetMapping
    public ResponseEntity<Void> deletePost(@RequestParam Long postSeq, HttpSession httpSession) {
        try {
            postservice.deletePost(postSeq, (String) httpSession.getAttribute("email"));
            return ResponseEntity.ok().build(); // 삭제 성공
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build(); // 게시물이 존재하지 않음
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 잘못된 요청
        }
    }

}
