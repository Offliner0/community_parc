package com.example.community_parc.controller;

import com.example.community_parc.dto.*;
import com.example.community_parc.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postservice;

    //게시글 리스트
    @GetMapping("/{gallery}/board/list/{page}")
    public Page<PostResponseDTO> getPostList(@PathVariable String gallery, @PathVariable int page) {

        return postservice.getPage(gallery,page);
    }

    //게시글 상세보기
    @GetMapping("/{gallery}/board/{postNum}")
    public ResponseEntity<GetPostDetailsResponseDTO> post(@PathVariable String gallery, @PathVariable Long postSeq, HttpServletRequest request) {
        GetPostDetailsResponseDTO post = postservice.getPost(postSeq,gallery);
        return ResponseEntity.ok(post);
    }

    //회원 게시글 등록
    @PostMapping("/{gallery}/postProc")
    public ResponseEntity<Void> postProc(@PathVariable String gallery,
                                         @RequestBody PostRequestDTO postRequestDTO,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            postservice.setMemberPost(postRequestDTO, customUserDetails.getUsername(), gallery);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            // 예외 처리 (로그 기록, 사용자에게 알림 등)
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 실패 시 500 상태 코드 반환
        }
    }

    //비회원 게시글 등록
    @PostMapping("/{gallery}/guestPostProc")
    public ResponseEntity<Void> postGuestProc(@PathVariable String gallery,
                                              @RequestBody PostRequestDTO postRequestDTO,
                                              HttpServletRequest request){
        String clientIp = request.getHeader("X-Forwarded-For");
        postservice.setUnknownPost(postRequestDTO, clientIp, gallery);

        return ResponseEntity.ok().build();
    }

    //회원 게시글 수정
    @PostMapping("/board/{gallery}/modify/{postSeq}")
    public ResponseEntity<Void> modifyPost(
            @PathVariable String gallery,
            @PathVariable Long postSeq,
            @RequestBody PostEditRequestDTO postRequestDTO,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ){

        if (postservice.modifyPost(postSeq,(String) customUserDetails.getUsername(),postRequestDTO)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //비회원 게시글 수정
    @PostMapping("/board/{gallery}/guestmodify/{postSeq}")
    public ResponseEntity<Void> modifyGuestPost(@PathVariable Long postSeq, @RequestBody GuestPostEditRequestDTO guestPostEditRequestDTO) {
        if (postservice.modifyGuestPost(postSeq,guestPostEditRequestDTO)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //회원 게시글 삭제
    @DeleteMapping("/board/{gallery}/{postSeq}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postSeq, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            postservice.deleteMemberPost(postSeq, customUserDetails.getUsername());
            return ResponseEntity.ok().build(); // 삭제 성공
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 잘못된 요청
        }
    }

    //비회원 게시글 삭제
    @DeleteMapping("/board/{gallery}/guest/{postSeq}")
    public ResponseEntity<Void> deleteGuestPost(@PathVariable Long postSeq, @RequestBody String password) {

        if (postservice.deleteGuestPost(postSeq,password)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
