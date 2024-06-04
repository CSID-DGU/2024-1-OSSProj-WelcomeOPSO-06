package com.backend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class MeetingResponse {

    private Long id;
    private String meetingName;
    private String userEmail;

    
}
