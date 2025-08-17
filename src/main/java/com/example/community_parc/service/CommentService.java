package com.example.community_parc.service;

import com.example.community_parc.domain.Comment;
import com.example.community_parc.domain.Member;
import com.example.community_parc.domain.Post;
import com.example.community_parc.dto.CommentDTO;
import com.example.community_parc.dto.CommentPainationDto;
import com.example.community_parc.dto.GuestCommentRequestDTO;
import com.example.community_parc.repository.comment.CommentRepository;
import com.example.community_parc.repository.MemberRepository;
import com.example.community_parc.repository.post.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    public List<CommentDTO.Response> getCommentsByPostId(UUID postId) {
        List<CommentDTO.Response> commentResponseDTOS = new ArrayList<>();
        List<CommentPainationDto> comments = commentRepository.commentPainations(postId,10,10);

        //삭제된 댓글은 내용없이 반환
        for (CommentPainationDto comment : comments) {

            if (comment.isDeleteYN()) commentResponseDTOS.add(CommentDTO.Response.deletedComment(comment));

            else commentResponseDTOS.add(CommentDTO.Response.fromComment(comment));
        }
        return commentResponseDTOS;
    }

    //회원 댓글 등록
    public void comment(UUID postNum, String email, CommentDTO.Request request) {
        Post post = postRepository.findById(postNum).orElseGet(Post::new);
        Member member = memberRepository.findByEmail(email);
        Comment comment = request.toComment();
        comment.setMember(member);
        comment.setPost(post);

        commentRepository.save(comment);
    }

    //비회원 댓글 등록
    public void guestComment(UUID postnNum, GuestCommentRequestDTO gusetCommentRequestDTO) {
        Post post = postRepository.findById(postnNum).orElseGet(Post::new);//나중에 수정

        Comment comment = gusetCommentRequestDTO.toComment();
        comment.setPost(post);

        commentRepository.save(comment);
    }

    //회원 댓글 수정
    public boolean commentModify(UUID replyNum, String email, CommentDTO.Request commentRequestDTO) {
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
    public boolean commentDelete(UUID replyNum, String email) {
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
    public boolean guestCommentDelete(UUID replyNum, String password) {
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
    public boolean guestCommentModify(UUID replyNum, CommentDTO.Request commentRequestDTO) {
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
    public void reply(UUID postNum, UUID replyNum,String email, CommentDTO.Request commentRequestDTO) {
        Post post = postRepository.findById(postNum).orElseGet(Post::new);
        Member member = memberRepository.findByEmail(email);

        Comment comment = commentRequestDTO.toComment(replyNum);

        comment.setPost(post);
        comment.setMember(member);

        commentRepository.save(comment);
    }

    //비회원 답글 등록
    public void guestReply(UUID postNum, UUID replyNum, GuestCommentRequestDTO commentRequestDTO) {
        Post post = postRepository.findById(postNum).orElseGet(Post::new);

        Comment comment = commentRequestDTO.toComment(replyNum);
        comment.setPost(post);

        commentRepository.save(comment);

    }

}
