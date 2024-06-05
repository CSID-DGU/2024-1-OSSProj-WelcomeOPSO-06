package com.backend.backend.dto;

import com.backend.backend.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList;

    @Builder
    private BoardResponseDto(Board entity, List<CommentResponseDto> list) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
        this.commentList = list;
    }

    private BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
        this.commentList = entity.getCommentList() != null
                ? entity.getCommentList().stream().map(CommentResponseDto::from).collect(Collectors.toList())
                : new ArrayList<>();
    }

    public static BoardResponseDto from(Board entity, List<CommentResponseDto> list) {
        return BoardResponseDto.builder()
                .entity(entity)
                .list(list)
                .build();
    }

    public static BoardResponseDto from(Board entity) {
        return new BoardResponseDto(entity);
    }

}
