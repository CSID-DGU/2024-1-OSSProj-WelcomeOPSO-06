package com.backend.backend.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import com.backend.backend.dto.AttendParticipantResponse;
import com.backend.backend.dto.AttendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.backend.entity.Attend;
import com.backend.backend.service.AttendService;

@RestController
@RequestMapping("/api")
public class AttendController {

    @Autowired
    private AttendService attendService;

    //참여자 출석 목록
    @GetMapping("/meetings/{meetingId}/attendance")
    public List<AttendParticipantResponse> getParticipantAttendance (@PathVariable Long meetingId, Principal principal) {
        String email = principal.getName();
        return attendService.getParticipantAttendance(meetingId,email);
    }

    //주최자 출석 목록-날짜별, 주최자만 접근가능
    @GetMapping("/meetings/{meetingId}/attendance-list/{date}")
    public List<AttendResponse> getOrganizerAttendance (@PathVariable Long meetingId, @PathVariable LocalDate date, Principal principal) {
        String email = principal.getName();//주최자
        return attendService.getOrganizerAttendance(meetingId,date,email);
    }

    @PostMapping("/meetings/{meetingId}/start-attendance")
    public ResponseEntity<String> startAttendance(@PathVariable Long meetingId, @RequestBody LocalDate date, Principal principal) {
        try {
            String email = principal.getName();//주최자
            // 모임 출석 정보 생성
            attendService.createAttendanceInfo(meetingId,date,email);
            return new ResponseEntity<>("출석부 생성 완료 meeting ID: " + meetingId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
