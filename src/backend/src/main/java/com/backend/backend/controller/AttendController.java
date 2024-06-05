package com.backend.backend.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.entity.Attend;
import com.backend.backend.service.AttendService;

@RestController
public class AttendController {

    @Autowired
    private AttendService attendService;

    // @PostMapping("/meetings/{meetingId}/check-in")
    // public  @ResponseBody ResponseEntity checkIn(@PathVariable Long meetingId, @RequestBody AttendRequest dto, Principal principal) {
    //     String email = principal.getName();//참여자
    //     Attend checked ;
    //     try {
    //         checked = attendService.checkIn(meetingId, dto, email);
            
    //     } catch (IllegalArgumentException e) {
    //         return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    //     } catch (IllegalStateException e) {
    //         return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
    //     } catch (Exception e) {
    //         return new ResponseEntity<String>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    //     return new ResponseEntity<Attend>(checked, HttpStatus.OK);
    // }

    //참여자 출석 목록
    @GetMapping("/meetings/{meetingId}/attendance")
    public List<Attend> getParticipantAttendance (@PathVariable Long meetingId, Principal principal) {
        String email = principal.getName();
        return attendService.getParticipantAttendance(meetingId,email);
    }

    //주최자 출석 목록-날짜별, 주최자만 접근가능
    @GetMapping("/meetings/{meetingId}/attendance-list/{date}")

    public List<Attend> getOrganizerAttendance (@PathVariable Long meetingId, @PathVariable LocalDate date, Principal principal) {
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
