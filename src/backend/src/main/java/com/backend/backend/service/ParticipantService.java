package com.backend.backend.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.backend.entity.Meeting;
import com.backend.backend.entity.Participant;
import com.backend.backend.entity.User;
import com.backend.backend.repository.MeetingRepository;
import com.backend.backend.repository.ParticipantRepository;
import com.backend.backend.repository.UserRepository;


@Service
public class ParticipantService {

    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParticipantRepository participantRepository;

    @Transactional
    public Participant register(Long meetingId, String email) {
    
        User user = userRepository.findByEmail(email).orElse(null);
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);

        // 사용자 또는 모임이 존재하지 않는 경우 null 반환
        if (user == null || meeting == null) {
            return null;
        }
        // 주최자가 참여자로 등록하려는 경우,
        if (Objects.equals(user.getId(), meeting.getUser().getId())) {
            return null;
        }

        if(participantRepository.findByMeetingIdAndUserId(meeting.getId(), user.getId()).orElse(null)!=null){//이미 등록된 참여자
            return null;
        }

        Participant participant = new Participant();
        participant.setUser(user);
        participant.setMeeting(meeting);

        return participantRepository.save(participant);//참여자 등록
    }

    //모임 탈퇴
    @Transactional
    public Participant withdraw(Long meetingId, String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        
        if (user == null) {
            return null;
        }
        Participant target = participantRepository.findByMeetingIdAndUserId(meetingId, user.getId()).orElse(null);
        
        //잘못된 요청 처리
        if(target ==null || !target.getUser().getId().equals(user.getId())){//타겟이 없거나, 타겟의 참여자 아이디가 요청한 사용자가 아닌경우
            return null;
        }
        //삭제 수행
        participantRepository.delete(target);
        return target;
    }

    
}
