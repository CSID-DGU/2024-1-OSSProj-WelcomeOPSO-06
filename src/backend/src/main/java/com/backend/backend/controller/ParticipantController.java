package com.backend.backend.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend.dto.ParticipantResponse;
import com.backend.backend.entity.Participant;
import com.backend.backend.repository.ParticipantRepository;
import com.backend.backend.service.ParticipantService;

@RestController
@RequestMapping("/api")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;
    @Autowired
    private ParticipantRepository participantRepository;
    
    //모임 참여자 등록
    @PostMapping("/meetings/{meetingId}/register")
    public ResponseEntity<String> register(@PathVariable Long meetingId, Principal principal){
        String email = principal.getName();
        try {
            Participant participant = participantService.register(meetingId, email);
            return new ResponseEntity<>("가입 처리 되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //모임 탈퇴
    @DeleteMapping("/meetings/{meetingId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable Long meetingId, Principal principal){
        String email = principal.getName();
        try {
            Participant participant =  participantService.withdraw(meetingId, email);
            return new ResponseEntity<>("탈퇴 처리되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //모임의 참여자 목록-누구나 볼수 있음
    @GetMapping("/meetings/{meetingId}/participants")
    public ResponseEntity<?> getAllParticipants(@PathVariable Long meetingId) {

        List<Participant> participantList = participantRepository.findByMeetingId(meetingId);
        
        if (participantList == null || participantList.isEmpty()) {//참여자 없음
            return new ResponseEntity<>("참여자가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        List<ParticipantResponse> responseList = new ArrayList<>();

        for (Participant participant : participantList){
            String participantEmail = participant.getUser().getEmail();
            responseList.add(new ParticipantResponse(participantEmail));
        }

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    
    }
    
        
    
}
