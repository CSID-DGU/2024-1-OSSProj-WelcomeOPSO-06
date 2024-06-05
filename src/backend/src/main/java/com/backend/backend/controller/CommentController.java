package com.backend.backend.controller;

import com.backend.backend.common.ApiResponseDto;
import com.backend.backend.common.SuccessResponse;
import com.backend.backend.dto.CommentRequestDto;
import com.backend.backend.dto.CommentResponseDto;
import com.backend.backend.entity.User;
import com.backend.backend.service.CommentService;
import com.backend.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    // 댓글 작성
    @PostMapping("/{boardId}/comments")   // 여기서 ID는 게시글의 id
    public ApiResponseDto<CommentResponseDto> createComment(@PathVariable Long boardId, @RequestBody CommentRequestDto requestDto, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        return commentService.createComment(boardId, requestDto, user);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")    // 여기서 ID는 댓글의 id
    public ApiResponseDto<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        return commentService.updateComment(commentId, requestDto, user);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")     // 여기서 ID는 댓글의 id
    public ApiResponseDto<SuccessResponse> deleteComment(@PathVariable Long commentId, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        return commentService.deleteComment(commentId, user);
    }

}
