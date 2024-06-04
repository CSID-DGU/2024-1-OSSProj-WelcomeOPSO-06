package com.backend.backend.dto;

import com.backend.backend.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponseDto {
    private Long id;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> childCommentList;

    @Builder
    private CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.contents = entity.getContents();
        this.username = entity.getUser().getUsername();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
        this.childCommentList = entity.getChildCommentList().stream().map(CommentResponseDto::from).toList();
    }

    public static CommentResponseDto from(Comment entity) {
        return CommentResponseDto.builder()
                .entity(entity)
                .build();
    }

}
