package com.backend.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.entity.Participant;
import com.backend.backend.repository.ParticipantRepository;
import com.backend.backend.service.ParticipantService;

@RestController
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;
    @Autowired
    private ParticipantRepository participantRepository;
    
    //모임 참여자 등록
    @PostMapping("/meetings/{meetingId}/register")
    public ResponseEntity<Participant> register(@PathVariable Long meetingId, Principal principal){
        String email = principal.getName();
        Participant participant = participantService.register(meetingId, email);
        return (participant !=null) ?
                ResponseEntity.status(HttpStatus.OK).body(participant):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //모임 탈퇴
    @DeleteMapping("/meetings/{meetingId}/withdraw")
    public ResponseEntity<Participant> withdraw(@PathVariable Long meetingId, Principal principal){
        String email = principal.getName();
        Participant participant =  participantService.withdraw(meetingId, email);
        return ( participant != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //모임의 참여자 목록-누구나 볼수 있음
    @GetMapping("/meetings/{meetingId}/participants")
    public List<Participant> getAllParticipants(@PathVariable Long meetingId) {

        List<Participant> participantList = participantRepository.findByMeetingId(meetingId);
        
        if (participantList == null) {//참여자 없음
            return null;
        }
        return participantList;
    
    }
    
        
    
}
