package com.backend.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.backend.backend.dto.MeetingRequest;
import com.backend.backend.dto.MeetingResponse;
import com.backend.backend.entity.Meeting;
import com.backend.backend.service.MeetingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;
    
    //주최자 모임 목록
    @GetMapping("/meetings/organizer")
    public List<MeetingResponse> getAllMeetings(Principal principal) {
        String email = principal.getName();
        return meetingService.getAllMeetings(email);
    }

    //참여자 모임 목록
    @GetMapping("/meetings/partcipant")
    public List<MeetingResponse> getMyMeetings(Principal principal) {
        String email = principal.getName();
        return meetingService.getMyMeetings(email);
    }
     // 모임 상세 페이지-누구나 모임아이디만 있으면 접근가능
    @GetMapping("/meetings/{meetingId}")
    public MeetingResponse show(@PathVariable Long meetingId){
        //데이터 가져오기
        Meeting meeting = meetingService.show(meetingId);
        return new MeetingResponse(meeting.getId(),meeting.getMeetingName(),meeting.getUser().getEmail());
    }

    //모임 생성-주최자
    @PostMapping("/meetings")
    public @ResponseBody ResponseEntity create(@RequestBody @Valid MeetingRequest dto, BindingResult bindingResult, Principal principal){
        
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        
        String email = principal.getName();
        Meeting meeting;
        
        try {
            meeting = meetingService.create(dto, email);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<MeetingResponse>(new MeetingResponse(meeting.getId(), meeting.getMeetingName(), meeting.getUser().getEmail()), HttpStatus.OK);
        
    }
    //모임이름 수정
    @PatchMapping("/meetings/{meetingId}")
    public @ResponseBody ResponseEntity update(@PathVariable Long meetingId, @RequestBody @Valid MeetingRequest dto,Principal principal ){
        String email = principal.getName();
        Meeting updated;
        
        try {
            updated = meetingService.update(meetingId,dto, email);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        MeetingResponse response = new MeetingResponse(updated.getId(), updated.getMeetingName(), updated.getUser().getEmail());
        return new ResponseEntity<MeetingResponse>(response, HttpStatus.OK);
    }

    //모임삭제
    @DeleteMapping("/meetings/{meetingId}")
    public ResponseEntity<Meeting> delete(@PathVariable Long meetingId, Principal principal){
        String email = principal.getName();
        Meeting deleted =  meetingService.delete(meetingId,email);
        return ( deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    
}
