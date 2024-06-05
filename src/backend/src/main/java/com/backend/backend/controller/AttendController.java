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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.dto.AttendParticipantResponse;
import com.backend.backend.dto.AttendResponse;
import com.backend.backend.service.AttendService;

@RestController
@RequestMapping("/api")
public class AttendController {

    @Autowired
    private AttendService attendService;

    //참여자 출석 목록
    @GetMapping("/meetings/{meetingId}/attendance")
    public ResponseEntity<?>  getParticipantAttendance (@PathVariable Long meetingId, Principal principal) {
        String email = principal.getName();
        try {
            List<AttendParticipantResponse> response = attendService.getParticipantAttendance(meetingId, email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);//출석기록 없음
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //주최자 출석 목록-날짜별, 주최자만 접근가능
    @GetMapping("/meetings/{meetingId}/attendance-list/{date}")
    public ResponseEntity<?> getOrganizerAttendance (@PathVariable Long meetingId, @PathVariable LocalDate date, Principal principal) {
        String email = principal.getName();//주최자
        try {
            List<AttendResponse> responseList = attendService.getOrganizerAttendance(meetingId,date,email);
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (IllegalArgumentException  e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);//참여자 ,출석기록없음
        }catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @PostMapping("/meetings/{meetingId}/start-attendance")
    public ResponseEntity<String> startAttendance(@PathVariable Long meetingId, @RequestBody LocalDate date, Principal principal) {
        try {
            String email = principal.getName();//주최자
            // 모임 출석 정보 생성
            attendService.createAttendanceInfo(meetingId,date,email);
            
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {//참여자 없음/이미 출석부 생성
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("출석부 생성 완료 meeting ID: " + meetingId, HttpStatus.OK);
    }



}
