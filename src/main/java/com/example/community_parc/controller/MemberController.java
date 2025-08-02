package com.example.community_parc.controller;

import com.example.community_parc.dto.CommentDTO;
import com.example.community_parc.dto.MyPageDTO;
import com.example.community_parc.dto.PostDTO;
import com.example.community_parc.service.MemberService;
import org.springframework.data.domain.Page;
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
        myPageDTO.setMyPosts(memberService.memberPostList(email,0));
        myPageDTO.setMyComments(memberService.memberCommentList(email,0));

        return myPageDTO;
    }

    @GetMapping("/mypage/{email}/posting/{page}")
    public Page<PostDTO.Response> memberPosting(@PathVariable String email, @PathVariable int page) {

        return memberService.memberPostList(email,page);
    }

    @GetMapping("/mypage/{email}/comment/{page}")
    public Page<CommentDTO.Response> memberComment(@PathVariable String email, @PathVariable int page) {

        return memberService.memberCommentList(email,page);
    }
}