package com.backend.backend.dto;

import java.time.LocalDate;

import com.backend.backend.domain.AttendStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class AttendRequest {

    private Long id;
    private LocalDate date;
    private AttendStatus attendStatus;

    

}
