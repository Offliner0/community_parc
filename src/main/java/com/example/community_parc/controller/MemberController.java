package com.example.community_parc.controller;

import com.example.community_parc.domain.Member;
import com.example.community_parc.dto.CommentResponseDTO;
import com.example.community_parc.dto.CustomUserDetails;
import com.example.community_parc.dto.MyPageDTO;
import com.example.community_parc.dto.PostResponseDTO;
import com.example.community_parc.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/mypage/{email}")
    public MyPageDTO myPage(@PathVariable String email) {

        MyPageDTO myPageDTO = memberService.myPage(email);
        myPageDTO.setMyPosts(memberService.memberPostList(email,1));
        myPageDTO.setMyComments(memberService.memberCommentList(email,1));

        return myPageDTO;
    }

    @GetMapping("/mypage/{email}/posting/{page}")
    public Page<PostResponseDTO> memberPosting(@PathVariable String email, @PathVariable int page) {

        return memberService.memberPostList(email,page);
    }

    @GetMapping("/mypage/{email}/comment/{page}")
    public Page<CommentResponseDTO> memberComment(@PathVariable String email, @PathVariable int page) {

        return memberService.memberCommentList(email,page);
    }
}