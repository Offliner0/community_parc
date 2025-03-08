package com.example.community_parc.service;

import com.example.community_parc.domain.Gallery;
import com.example.community_parc.domain.Member;
import com.example.community_parc.domain.Post;
import com.example.community_parc.dto.*;
import com.example.community_parc.repository.GalleryRepository;
import com.example.community_parc.repository.MemberRepository;
import com.example.community_parc.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final GalleryRepository galleryRepository;
    private final PostRepository postRepository;

    public List<PostResponseDTO> getPosts(String gallery) {

        Gallery gallery1 = galleryRepository.findByGalleryName(gallery);

        return postRepository.findByGallery(gallery1).stream().map(PostResponseDTO::fromPost).collect(Collectors.toList());

    }

    //페이지
    public Page<PostResponseDTO> getPage(String gallery, int page) {
        int pageLimit = 10; //한 페이지 당 게시글 수
        Pageable pageable = PageRequest.of(page, pageLimit);
        Gallery gallery1 = galleryRepository.findByGalleryName(gallery);

        Page<Post> posts = postRepository.findByGallery(gallery1,pageable);

        return posts.map(PostResponseDTO::fromPost);

    }

    //회원 게시글
    public void setMemberPost(PostRequestDTO postRequestDTO, String email, String gallery) {
        Member member = memberRepository.findByEmail(email);
        Gallery galleryObj = galleryRepository.findByGalleryName(gallery);
        Post post = postRequestDTO.toPost(member,galleryObj);
        postRepository.save(post);
    }

    //비회원 게시글
    public void setUnknownPost(PostRequestDTO postRequestDTO, String clientIp, String gallery) {
        Gallery galleryObj = galleryRepository.findByGalleryName(gallery);
        System.out.println(galleryObj.getGalleryId());
        if(galleryObj == null) {
            System.out.println("null gallery");
        }
        Post post = postRequestDTO.toPost(clientIp,galleryObj);

        System.out.println("before save post");
        System.out.println(post.getGallery().getGalleryId());

        postRepository.save(post);
    }

    //게시글 상세보기
    public GetPostDetailsResponseDTO getPost(Long postSeq, String gallery) {
        Post post = postRepository.findById(postSeq).orElseThrow(NoSuchElementException::new);
        if (post.getDeleteYN()!=0){
            throw new NoSuchElementException("게시물이 존재하지 않습니다.");//삭제된 게시물 경고 페이지로 이동
        }
        return GetPostDetailsResponseDTO.fromPost(post);
    }

    //회원 게시글 삭제
    public void deleteMemberPost(Long postSeq, String email) {
        Member member = memberRepository.findByEmail(email);
        Post post = postRepository.findById(postSeq).orElseThrow(()->new NoSuchElementException("잘못된 접근입니다."));
        if (post.getDeleteYN() == 0 && Objects.equals(post.getMember().getMemberId(), member.getMemberId())){
            post.setDeleteYN(1);
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
        }else throw new IllegalArgumentException("잘못된 요청입니다."); //잘못된 요청 예외 처리
    }

    //회원 게시글 수정
    public Boolean modifyPost(Long postSeq, String email, PostEditRequestDTO postEditRequestDTO) {
        Member member = memberRepository.findByEmail(email);
        Post post = postRepository.findById(postSeq).orElseThrow(NoSuchElementException::new);

        if (post.getDeleteYN()==0 && Objects.equals(post.getMember().getMemberId(), member.getMemberId())){
            post.setTitle(postEditRequestDTO.getTitle());
            post.setContent(postEditRequestDTO.getContent());
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
            return true;
        }
        return false;
    }

    //비회원 게시글 삭제
    public Boolean deleteGuestPost(Long postSeq, String password) {
        Post post = postRepository.findById(postSeq).orElseThrow(NoSuchElementException::new);

        if (post.getDeleteYN() == 0 && Objects.equals(post.getPassword(), password)){
            post.setDeleteYN(1);
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
            return true;
        }
        return false;
    }

    //비회원 게시글 수정
    public Boolean modifyGuestPost(Long postSeq, GuestPostEditRequestDTO guestPostEditRequestDTO) {
        Post post = postRepository.findById(postSeq).orElseThrow(NoSuchElementException::new);

        if (post.getDeleteYN() == 0 && Objects.equals(post.getPassword(), guestPostEditRequestDTO.getPassword())){
            post.setTitle(guestPostEditRequestDTO.getTitle());
            post.setContent(guestPostEditRequestDTO.getContent());
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
            return true;
        }
        return false;
    }
}
