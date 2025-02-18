package com.example.community_parc.controller;

import com.example.community_parc.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/member")
public class MemberController {
    MemberService memberService;

}