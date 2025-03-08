package com.example.community_parc.service;

import com.example.community_parc.domain.Comment;
import com.example.community_parc.domain.Member;
import com.example.community_parc.domain.Post;
import com.example.community_parc.dto.CommentRequestDTO;
import com.example.community_parc.dto.CommentResponseDTO;
import com.example.community_parc.dto.MyPageDTO;
import com.example.community_parc.dto.PostResponseDTO;
import com.example.community_parc.repository.CommentRepository;
import com.example.community_parc.repository.MemberRepository;
import com.example.community_parc.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public MemberService(MemberRepository memberRepository,
                         PostRepository postRepository,
                         CommentRepository commentRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public MyPageDTO myPage(String email) {

        Member member = memberRepository.findByEmail(email);
        MyPageDTO myPageDTO = new MyPageDTO();
        myPageDTO.setNickname(member.getNickname());
        myPageDTO.setCreatedAt(member.getCreatedAt());

        return myPageDTO;
    }

    public Page<PostResponseDTO> memberPostList(String email , int page){

        int pageLimit = 10; //한 페이지 당 게시글 수
        Pageable pageable = PageRequest.of(page, pageLimit);
        Member member = memberRepository.findByEmail(email);

        Page<Post> posts = postRepository.findByMember(member,pageable);

        return posts.map(PostResponseDTO::fromPost);

    }

    public Page<CommentResponseDTO> memberCommentList(String email, int page){

        int pageLimit = 10; //한 페이지 당 게시글 수
        Pageable pageable = PageRequest.of(page, pageLimit);
        Member member = memberRepository.findByEmail(email);

        Page<Comment> comments = commentRepository.findByMember(member,pageable);

        return comments.map(CommentResponseDTO::fromComment);
    }



}
