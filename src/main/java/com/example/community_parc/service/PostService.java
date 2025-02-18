package com.example.community_parc.service;

import com.example.community_parc.domain.Gallery;
import com.example.community_parc.domain.Member;
import com.example.community_parc.domain.Post;
import com.example.community_parc.dto.PostGetResponseDTO;
import com.example.community_parc.dto.PostRequestDTO;
import com.example.community_parc.repository.GalleryRepository;
import com.example.community_parc.repository.MemberRepository;
import com.example.community_parc.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    MemberRepository memberRepository;
    GalleryRepository galleryRepository;
    PostRepository postRepository;

    public void setMemberPost(PostRequestDTO postRequestDTO, String email, String gallery) { //회원 게시글
        Member member = memberRepository.findByEmail(email);
        Gallery galleryObj = galleryRepository.findByGalleryName(gallery);
        Post post = postRequestDTO.toPost(member,galleryObj);
        postRepository.save(post);
    }

    public void setUnknownPost(PostRequestDTO postRequestDTO, String clientIp, String gallery) { //비회원 게시글
        Gallery galleryObj = galleryRepository.findByGalleryName(gallery);
        Post post = postRequestDTO.toPost(clientIp,galleryObj);
        postRepository.save(post);
    }

    public PostGetResponseDTO getPost(Long postSeq, String gallery) { //게시글 상세보기
        Post post = postRepository.findById(postSeq).orElseThrow(NoSuchElementException::new);
        if (post.getDeleteYN()!=0){
            throw new NoSuchElementException("게시물이 존재하지 않습니다.");//삭제된 게시물 경고 페이지로 이동
        }
        return new PostGetResponseDTO(post);
    }

    public boolean unknownPwCheck(Long postSeq, String pw){ //비회원 비밀번호 검증
        Post post = postRepository.findById(postSeq).orElseThrow(NoSuchElementException::new);
        return post.getPassword().equals(pw);
    }

    public void deletePost(Long postSeq, String email) { //게시글 삭제
        Member member = memberRepository.findByEmail(email);
        Post post = postRepository.findById(postSeq).orElseThrow(()->new NoSuchElementException("잘못된 접근입니다."));
        if (post.getDeleteYN()!=0 && Objects.equals(post.getMember().getMemberId(), member.getMemberId())){
            postRepository.delete(post);
        }else throw new IllegalArgumentException("잘못된 요청입니다."); //잘못된 요청 예외 처리
    }
}
