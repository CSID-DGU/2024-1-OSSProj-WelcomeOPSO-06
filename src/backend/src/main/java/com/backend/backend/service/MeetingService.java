package com.backend.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.backend.dto.MeetingRequest;
import com.backend.backend.entity.Attend;
import com.backend.backend.entity.Board;
import com.backend.backend.entity.Meeting;
import com.backend.backend.entity.Participant;
import com.backend.backend.entity.User;
import com.backend.backend.repository.AttendRepository;
import com.backend.backend.repository.BoardRepository;
import com.backend.backend.repository.MeetingRepository;
import com.backend.backend.repository.ParticipantRepository;
import com.backend.backend.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private AttendRepository attendRepository;
    @Autowired
    private BoardRepository boardRepository;

    //주최자 모임목록
    public List<Meeting> getAllMeetings(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return meetingRepository.findByUserId(user.getId());
    }

    //참여자 모임목록
    public List<Meeting> getMyMeetings(String email) {
        User user = userRepository.findByEmail(email).orElse(null);//참여자
        List<Participant> participants =  participantRepository.findByUserId(user.getId());
        
        if (participants == null || participants.isEmpty()) {
            return null;//참여중인 모임 없음
        }

        // 참여자가 참여한 모임 목록 추출
        List<Meeting> myMeetings = new ArrayList<>();

        for (Participant participant : participants) {
            myMeetings.add(participant.getMeeting());
        }

        return myMeetings;

    }

    public Meeting show(Long meetingId) {
        return meetingRepository.findById(meetingId).orElse(null);
    }

    @Transactional
    public Meeting create(MeetingRequest dto, String email){
        Meeting newMeeting = toEntity(dto, email);

        if(newMeeting.getId() != null){
            return null;
        }
        return meetingRepository.save(newMeeting);
    }

     // DTO를 엔티티로 변환하는 메서드
    private Meeting toEntity(MeetingRequest dto, String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        Long id = dto.getId();
        String meetingName = dto.getMeetingName();
        return new Meeting(id,meetingName,user);
    }

    
    @Transactional
    public Meeting update(Long meetingId, MeetingRequest dto, String email) {
        //엔티티 변환
        Meeting updateMeeting = toEntity(dto,email);
        //타겟 조회
        Meeting target = meetingRepository.findById(meetingId).orElse(null);
        //잘못된 요청 처리
        
        if(target == null || meetingId!=updateMeeting.getId() || target.getUser().getId()!=updateMeeting.getUser().getId()){
            log.info("잘못된 요청! id:{},meeting:{}",meetingId,meetingRepository.toString());
            return null;
        }
        target.patch(updateMeeting);
        Meeting updated =meetingRepository.save(target);
        return updated;
    }

    @Transactional
    public Meeting delete(Long meetingId, String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        //대상 찾기
        Meeting target = meetingRepository.findById(meetingId).orElse(null);
        //잘못된 요청 처리
        if(target ==null || target.getUser().getId()!=user.getId()){//타겟이 없거나, 타겟의 주최자 아이디가 요청한 사용자가 아닌ㄱ
            return null;
        }
        //삭제 수행
        //홍보게시글 삭제
        Board board = boardRepository.findByMeetingId(meetingId).orElse(null);
        if (board != null){
            boardRepository.delete(board);
        }

        //참여자 삭제
        List<Participant> participantList = participantRepository.findByMeetingId(meetingId);
        for (Participant participant : participantList) {
            
            //출석부 삭제
            List<Attend> attendList = attendRepository.findByParticipant(participant);
            for (Attend attend : attendList) {
                attendRepository.delete(attend);
            }
            participantRepository.delete(participant);
        }
        
        meetingRepository.delete(target);
        return target;
    }


    

    
}
