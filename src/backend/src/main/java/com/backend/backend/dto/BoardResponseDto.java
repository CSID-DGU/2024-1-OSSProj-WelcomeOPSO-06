package com.backend.backend.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.backend.backend.entity.Board;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long id;
    private Long meetingId;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList;

    @Builder
    public BoardResponseDto(Long id, Long meetingId, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, List<CommentResponseDto> commentList) {
        this.id = id;
        this.meetingId = meetingId;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.commentList = commentList;
    }

    public static BoardResponseDto from(Board entity, List<CommentResponseDto> commentList) {
        return BoardResponseDto.builder()
                .id(entity.getId())
                .meetingId(entity.getMeeting().getId())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .commentList(commentList)
                .build();
    }

    public static BoardResponseDto from(Board entity) {
        List<CommentResponseDto> commentResponseDtoList = entity.getCommentList() != null
                ? entity.getCommentList()
                : new ArrayList<>();

        return new BoardResponseDto(
                entity.getId(),
                entity.getMeeting().getId(),
                entity.getTitle(),
                entity.getContents(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                commentResponseDtoList
        );
    }
}
