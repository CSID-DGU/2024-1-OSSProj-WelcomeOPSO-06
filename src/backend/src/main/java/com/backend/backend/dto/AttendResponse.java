package com.backend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@ToString
@Getter
public class AttendResponse {
    private LocalDate date;
    private String participantEmail;
    private String attendStatus;
}
