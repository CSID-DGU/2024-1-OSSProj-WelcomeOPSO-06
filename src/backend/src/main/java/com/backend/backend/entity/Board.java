package com.backend.backend.entity;

import com.backend.backend.dto.BoardRequestsDto;
import com.backend.backend.dto.CommentResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 65535)
    private String contents;

    @OneToOne
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Builder
    private Board(BoardRequestsDto requestsDto, Meeting meeting) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.meeting = meeting;
    }

    public void update(BoardRequestsDto requestsDto, Meeting meeting) {
        this.title = requestsDto.getTitle();
        this.contents = requestsDto.getContents();
        this.meeting = meeting;
    }

    public static Board of(BoardRequestsDto requestsDto, Meeting meeting) {
        return Board.builder()
                .requestsDto(requestsDto)
                .meeting(meeting)
                .build();
    }

    // 댓글 목록을 CommentResponseDto로 반환하는 메서드
    public List<CommentResponseDto> getCommentList() {
        return comments != null
                ? comments.stream().map(CommentResponseDto::from).collect(Collectors.toList())
                : null;
    }
}
