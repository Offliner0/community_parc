package com.example.community_parc.service;

import com.example.community_parc.domain.Comment;
import com.example.community_parc.domain.Gallery;
import com.example.community_parc.domain.Member;
import com.example.community_parc.domain.Post;
import com.example.community_parc.dto.CommentRequestDTO;
import com.example.community_parc.dto.CommentResponseDTO;
import com.example.community_parc.dto.GuestCommentRequestDTO;
import com.example.community_parc.repository.CommentRepository;
import com.example.community_parc.repository.MemberRepository;
import com.example.community_parc.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommentService(CommentRepository commentRepository,PostRepository postRepository,MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    //리스트 반환
    public Page<CommentResponseDTO> getComments(Long postId,int page) {
        int pageLimit = 10; //한 페이지 당 게시글 수
        //if (page < 1)
        Pageable pageable = PageRequest.of(page-1, pageLimit);

        Post post = postRepository.findById(postId).get();
        Page<Comment> comments = commentRepository.findByPost(post,pageable);

        return comments.map(CommentResponseDTO::fromComment);
    }

    //회원 댓글 등록
    public void comment(Long postNum, String email, CommentRequestDTO commentRequestDTO) {
        Post post = postRepository.findById(postNum).orElseGet(Post::new);
        Member member = memberRepository.findByEmail(email);
        Comment comment = commentRequestDTO.toComment();
        comment.setAuthor(member.getNickname());
        comment.setMember(member);
        comment.setPost(post);

        commentRepository.save(comment);
    }

    //비회원 댓글 등록
    public void guestComment(Long postNum, GuestCommentRequestDTO guestCommentRequestDTO) {
        Post post = postRepository.findById(postNum).orElseGet(Post::new);//나중에 수정

        Comment comment = guestCommentRequestDTO.toComment();
        comment.setPost(post);

        commentRepository.save(comment);
    }

    //회원 댓글 수정
    public boolean commentModify(Long replyNum, String email, CommentRequestDTO commentRequestDTO) {
        Comment comment = commentRepository.findById(replyNum).orElseGet(Comment::new);
        Member member = memberRepository.findByEmail(email);

        if (!comment.isDeleteYN() && Objects.equals(member.getEmail(), email)) {
            comment.setContent(commentRequestDTO.getContent());
            comment.setUpdatedAt(LocalDateTime.now());

            commentRepository.save(comment);
            return true;
        }
        return false;
    }

    //회원 댓글 삭제
    public boolean commentDelete(Long replyNum, String email) {
        Comment comment = commentRepository.findById(replyNum).orElseGet(Comment::new);
        Member member = memberRepository.findByEmail(email);
        if (!comment.isDeleteYN() && Objects.equals(member.getEmail(), email)) {
            comment.setDeleteYN(true);
            comment.setUpdatedAt(LocalDateTime.now());

            commentRepository.save(comment);
            return true;
        }
        return false;
    }

    //비회원 댓글 삭제
    public boolean guestCommentDelete(Long replyNum, String password) {
        Comment comment = commentRepository.findById(replyNum).orElseGet(Comment::new);

        if (!comment.isDeleteYN() && Objects.equals(comment.getPassword(), password)) {
            comment.setDeleteYN(true);
            comment.setUpdatedAt(LocalDateTime.now());

            commentRepository.save(comment);
            return true;
        }
        return false;
    }

    //비회원 댓글 수정
    public boolean guestCommentModify(Long replyNum, CommentRequestDTO commentRequestDTO) {
        Comment comment = commentRepository.findById(replyNum).orElseGet(Comment::new);
        if (!comment.isDeleteYN() && Objects.equals(comment.getPassword(), commentRequestDTO.getPassword())) {
            comment.setContent(commentRequestDTO.getContent());
            comment.setUpdatedAt(LocalDateTime.now());
            commentRepository.save(comment);

            return true;
        }
        return false;
    }

    //회원 답글 등록
    public void reply(Long postNum, Long replyNum,String email, CommentRequestDTO commentRequestDTO) {
        Post post = postRepository.findById(postNum).orElseGet(Post::new);
        Member member = memberRepository.findByEmail(email);

        Comment comment = commentRequestDTO.toComment(replyNum);

        comment.setPost(post);
        comment.setMember(member);

        commentRepository.save(comment);
    }

    //비회원 답글 등록
    public void guestReply(Long postNum, Long replyNum, GuestCommentRequestDTO commentRequestDTO) {
        Post post = postRepository.findById(postNum).orElseGet(Post::new);

        Comment comment = commentRequestDTO.toComment(replyNum);
        comment.setPost(post);

        commentRepository.save(comment);

    }

}
