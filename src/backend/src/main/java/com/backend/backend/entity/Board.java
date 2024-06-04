package com.backend.backend.entity;

import com.backend.backend.dto.BoardRequestsDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
}
