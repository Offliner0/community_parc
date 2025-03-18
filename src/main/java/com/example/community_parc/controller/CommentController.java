package com.example.community_parc.controller;

import com.example.community_parc.dto.CommentRequestDTO;
import com.example.community_parc.dto.CommentResponseDTO;
import com.example.community_parc.dto.CustomUserDetails;
import com.example.community_parc.dto.GuestCommentRequestDTO;
import com.example.community_parc.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //댓글 불러오기
    @GetMapping("/{postNum}/{commentPage}")
    public Page<CommentResponseDTO> getComment(@PathVariable Long postNum,@PathVariable int commentPage){
        return  commentService.getComments(postNum, commentPage);
    }

    //회원 댓글
    @PostMapping("/{postNum}")
    public ResponseEntity<Object> comment(@PathVariable Long postNum, @RequestBody CommentRequestDTO commentRequestDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        commentService.comment(postNum,customUserDetails.getUsername(),commentRequestDTO);

        return ResponseEntity.ok().build();
    }

    //비회원 댓글
    @PostMapping("/guestcomment/{postNum}")
    public ResponseEntity<Object> gusetComment(@PathVariable Long postNum, @RequestBody GuestCommentRequestDTO guestCommentRequestDTO){

        commentService.guestComment(postNum, guestCommentRequestDTO);

        return ResponseEntity.ok().build();
    }

    //회원 댓글 수정
    @PostMapping("/modify/{replyNum}")
    public ResponseEntity<Object> modifyComment(@PathVariable Long replyNum,
                                                @RequestBody CommentRequestDTO commentRequestDTO,
                                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();

        if (commentService.commentModify(replyNum,email,commentRequestDTO)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    //비회원 댓글 수정
    @PostMapping("/gusetmodify/{replyNum}")
    public ResponseEntity<Object> modifyGuestComment(@PathVariable Long replyNum, @RequestBody CommentRequestDTO commentRequestDTO){
        if (commentService.guestCommentModify(replyNum,commentRequestDTO)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //회원 댓글 삭제
    @DeleteMapping("/{replyNum}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long replyNum,@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (commentService.commentDelete(replyNum,customUserDetails.getUsername())){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //비회원 댓글 삭제
    @DeleteMapping("/guestcomment/{replyNum}")
    public ResponseEntity<Object> deleteGuestComment(@PathVariable Long replyNum,@RequestBody String password){
        if (commentService.guestCommentDelete(replyNum,password)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //회원 답글
    @PostMapping("/reply/{postNum}/{replyNum}")
    public ResponseEntity<Object> reply(@PathVariable Long postNum,
                                        @PathVariable Long replyNum,
                                        @RequestBody CommentRequestDTO commentRequestDTO,
                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String email = customUserDetails.getUsername();

        commentService.reply(postNum, replyNum,email,commentRequestDTO);

        return ResponseEntity.ok().build();
    }

    //비회원 답글
    @PostMapping("/guestreply/{postNum}/{replyNum}")
    public ResponseEntity<Object> guestReply(@PathVariable Long postNum,
                                             @PathVariable Long replyNum,
                                             @RequestBody GuestCommentRequestDTO guestCommentRequestDTO){

        commentService.guestReply(postNum,replyNum,guestCommentRequestDTO);
        return ResponseEntity.ok().build();
    }
}
