package com.backend.backend.controller;

import com.backend.backend.common.ApiResponseDto;
import com.backend.backend.dto.BoardRequestsDto;
import com.backend.backend.dto.BoardResponseDto;
import com.backend.backend.entity.User;
import com.backend.backend.service.BoardService;
import com.backend.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    // 게시글 전체 목록 조회
    @GetMapping("/api/posts")
    public ApiResponseDto<List<BoardResponseDto>> getPosts() {
        return boardService.getPosts();
    }

    // 게시글 작성
    @PostMapping("/api/post")
    public ApiResponseDto<BoardResponseDto> createPost(@RequestBody BoardRequestsDto requestsDto, @RequestParam Long userId) {
        User user = userService.getUserById(userId);
        return boardService.createPost(requestsDto, user);
    }

    // 선택된 게시글 조회
    @GetMapping("/api/post/{id}")
    public ApiResponseDto<BoardResponseDto> getPost(@PathVariable Long id) {
        return boardService.getPost(id);
    }

    // 선택된 게시글 수정
    @PutMapping("/api/post/{id}")
    public ApiResponseDto<BoardResponseDto> updatePost(@PathVariable Long id, @RequestBody BoardRequestsDto requestsDto, @RequestParam Long userId) {
        User user = userService.getUserById(userId);
        return boardService.updatePost(id, requestsDto, user);
    }

}
