package com.backend.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.common.ApiResponseDto;
import com.backend.backend.dto.BoardRequestsDto;
import com.backend.backend.dto.BoardResponseDto;
import com.backend.backend.service.BoardService;
import com.backend.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    // 게시글 전체 목록 조회
    @GetMapping("/main")
    public ApiResponseDto<List<BoardResponseDto>> getPosts() {
        return boardService.getPosts();
    }

    // 게시글 작성
    @PostMapping("/main/{meetingId}/post")
    public ApiResponseDto<BoardResponseDto> createPost(@RequestBody BoardRequestsDto requestsDto, @PathVariable Long meetingId, Principal principal) {
        String email = principal.getName();//작성자 아이디
        return boardService.createPost(requestsDto, meetingId, email);
    }

    // 선택된 게시글 조회
    @GetMapping("/main/post/{boardId}")
    public ApiResponseDto<BoardResponseDto> getPost(@PathVariable Long boardId) {
        return boardService.getPost(boardId);
    }

    // 선택된 게시글 수정
    @PutMapping("/main/post/{boardId}")
    public ApiResponseDto<BoardResponseDto> updatePost(@PathVariable Long boardId, @RequestBody BoardRequestsDto requestsDto, Principal principal) {
        String email = principal.getName();//작성자 아이디
        return boardService.updatePost(boardId, requestsDto, email);
    }

}
