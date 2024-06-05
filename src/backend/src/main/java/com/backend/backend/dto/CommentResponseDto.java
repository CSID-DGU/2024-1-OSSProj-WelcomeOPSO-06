package com.backend.backend.dto;

import com.backend.backend.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseDto {
    private Long id;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> childCommentList;

    @Builder
    public CommentResponseDto(Long id, String contents, String username, LocalDateTime createdAt, LocalDateTime modifiedAt, List<CommentResponseDto> childCommentList) {
        this.id = id;
        this.contents = contents;
        this.username = username;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.childCommentList = childCommentList;
    }

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .contents(comment.getContents())
                .username(comment.getUser() != null ? comment.getUser().getUsername() : "Unknown")
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .childCommentList(comment.getChildCommentList() != null
                        ? comment.getChildCommentList().stream().map(CommentResponseDto::from).collect(Collectors.toList())
                        : null)
                .build();
    }
}
