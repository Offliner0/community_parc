package com.example.community_parc.controller;

import com.example.community_parc.dto.CommentDTO;
import com.example.community_parc.dto.CommentDTO.Request;
import com.example.community_parc.dto.CommentDTO.Response;
import com.example.community_parc.dto.CustomUserDetails;
import com.example.community_parc.dto.GuestCommentRequestDTO;
import com.example.community_parc.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //댓글 불러오기
    @GetMapping("/{PostNum}")
    public List<CommentDTO.Response> getComment(@PathVariable UUID postNum){
        return  commentService.getCommentsByPostId(postNum);
    }

    //회원댓글
    @PostMapping("/{postNum}")
    @PreAuthorize("hasRole(UserRole.USER)")
    public ResponseEntity<Object> comment(@PathVariable UUID postNum, @RequestBody CommentDTO.Request request, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        commentService.comment(postNum,customUserDetails.getUsername(),request);

        return ResponseEntity.ok().build();
    }

    //비회원 댓글
    @PostMapping("/guestcomment/{postNum}")
    public ResponseEntity<Object> gusetComment(@PathVariable UUID postNum, @RequestBody GuestCommentRequestDTO guestCommentRequestDTO){

        commentService.guestComment(postNum, guestCommentRequestDTO);

        return ResponseEntity.ok().build();
    }

    //회원 댓글 수정
    @PostMapping("/modify/{replyNum}")
    public ResponseEntity<Object> modifyComment(@PathVariable UUID replyNum,
                                                @RequestBody CommentDTO.Request commentRequestDTO,
                                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();

        if (commentService.commentModify(replyNum,email,commentRequestDTO)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    //비회원 댓글 수정
    @PostMapping("/gusetmodify/{replyNum}")
    public ResponseEntity<Object> modifyGuestComment(@PathVariable UUID replyNum, @RequestBody CommentDTO.Request commentRequestDTO){
        if (commentService.guestCommentModify(replyNum,commentRequestDTO)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //회원 댓글 삭제
    @DeleteMapping("/{replyNum}")
    public ResponseEntity<Object> deleteComment(@PathVariable UUID replyNum,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (commentService.commentDelete(replyNum,customUserDetails.getUsername())){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //비회원 댓글 삭제
    @DeleteMapping("/guestcomment/{replyNum}")
    public ResponseEntity<Object> deleteGuestComment(@PathVariable UUID replyNum,@RequestBody String password){
        if (commentService.guestCommentDelete(replyNum,password)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //회원 답글
    @PostMapping("/reply/{postNum}/{replyNum}")
    public ResponseEntity<Object> reply(@PathVariable UUID postNum,
                                        @PathVariable UUID replyNum,
                                        @RequestBody CommentDTO.Request commentRequestDTO,
                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();

        commentService.reply(postNum, replyNum,email,commentRequestDTO);

        return ResponseEntity.ok().build();
    }

    //비회원 답글
    @PostMapping("/guestreply/{postNum}/{replyNum}")
    public ResponseEntity<Object> guestReply(@PathVariable UUID postNum,
                                             @PathVariable UUID replyNum,
                                             @RequestBody GuestCommentRequestDTO guestCommentRequestDTO){

        commentService.guestReply(postNum,replyNum,guestCommentRequestDTO);
        return ResponseEntity.ok().build();
    }
}
