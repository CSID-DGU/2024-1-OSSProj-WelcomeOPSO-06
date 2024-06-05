package com.backend.backend.dto;

import com.backend.backend.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<CommentResponseDto> childCommentList;

    @Builder
    public CommentResponseDto(Long id, String contents, String username, LocalDateTime createdAt, LocalDateTime modifiedAt, List<CommentResponseDto> childCommentList) {
        this.id = id;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.childCommentList = childCommentList;
    }

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .contents(comment.getContents())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .childCommentList(comment.getChildCommentList() != null
                        ? comment.getChildCommentList().stream().map(CommentResponseDto::from).collect(Collectors.toList())
                        : null)
                .build();
    }
}
