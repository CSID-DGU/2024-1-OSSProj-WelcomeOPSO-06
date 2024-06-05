package com.backend.backend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.backend.domain.AttendStatus;
import com.backend.backend.dto.AttendParticipantResponse;
import com.backend.backend.dto.AttendRequest;
import com.backend.backend.dto.AttendResponse;
import com.backend.backend.entity.Attend;
import com.backend.backend.entity.Meeting;
import com.backend.backend.entity.Participant;
import com.backend.backend.entity.User;
import com.backend.backend.repository.AttendRepository;
import com.backend.backend.repository.MeetingRepository;
import com.backend.backend.repository.ParticipantRepository;
import com.backend.backend.repository.UserRepository;

@Service
public class AttendService {

    @Autowired
    private AttendRepository attendRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private ParticipantRepository participantRepository;

    @Transactional
    public Attend checkIn(Long meetingId, AttendRequest dto, String email) {
        User user = userRepository.findByEmail(email).orElse(null);//참여자
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        
        //유저나 모임이 없는경우
        if (user == null || meeting == null) {
            throw new IllegalArgumentException("참여자 또는 모임이 없습니다.");
        }
        //참여자정보 확인
        Participant participant = participantRepository.findByMeetingIdAndUserId(meetingId, user.getId()).orElse(null);
        if (participant == null) {
            throw new IllegalArgumentException("해당 모임의 참여자가 아닙니다.");
        }
    
        // 출석부가 생성되었는지 확인
        Attend existingAttend = attendRepository.findByParticipantAndDate(participant, dto.getDate()).orElse(null);
        if (existingAttend==null) {
            throw new IllegalStateException("출석이 시작되지 않았습니다.");
        }

        // 출석 상태가 "결석 상태"인 경우에만 출석을 결석
        if (existingAttend.getAttendStatus() != AttendStatus.ABSENCE) {
            throw new IllegalStateException("이미 출석체크가 완료되었습니다.");
        }
        // 출석 상태를 변경하여 저장
        existingAttend.setAttendStatus(dto.getAttendStatus());
        return attendRepository.save(existingAttend);
    }

    //참여자 출석 목록
    public List<AttendParticipantResponse> getParticipantAttendance(Long meetingId, String email) {
        User user = userRepository.findByEmail(email).orElse(null);//참여자
        Participant participant = participantRepository.findByMeetingIdAndUserId(meetingId, user.getId()).orElse(null);
        if(participant==null){
            throw new IllegalArgumentException("참여중인 모임이 아닙니다.");//참여하고 있는 모임이 없다.
        }
        List<Attend> attendList = attendRepository.findByParticipant(participant);
        if(attendList==null || attendList.isEmpty()){
            throw new IllegalStateException("출석기록이 없습니다.");
        }

        List<AttendParticipantResponse> responseList = new ArrayList<>();
        for (Attend attend : attendList){
            responseList.add(new AttendParticipantResponse(attend.getDate(),attend.getAttendStatus().toString()));
        }
        return responseList;
    }

    //주최자 출석 목록
    public List<AttendResponse> getOrganizerAttendance(Long meetingId, LocalDate date, String email) {
        User user = userRepository.findByEmail(email).orElse(null);//주최자
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        if(meeting.getId()!=meetingId || meeting==null){
            throw new IllegalArgumentException("모임이 없습니다.");//미팅확인
        }
        if(meeting.getUser().getId()!=user.getId()){
            throw new IllegalArgumentException("주최자가 아닙니다.");//주최자가 아닌경우
        }
        //모임 아이디로 참여자 찾기
        List<Participant> participants = participantRepository.findByMeetingId(meetingId);
        if(participants==null || participants.isEmpty()){
            throw new IllegalStateException("참여자가 없습니다.");//참여자 없음
        }
        // 해당 날짜의 각 참여자의 출석 정보 가져오기
        List<AttendResponse> responseList = new ArrayList<>();

        for (Participant participant : participants) {
            Attend attendance = attendRepository.findByParticipantAndDate(participant, date).orElse(null);
            if (attendance != null) {//해당날짜에 출석 정보가 없는경우(나중에 가입한경우)
                responseList.add(new AttendResponse(date, participant.getUser().getEmail(),attendance.getAttendStatus().toString()));
            }
        }
        if(responseList==null || responseList.isEmpty()){//출석정보가 없는경우
            throw new IllegalStateException("출석기록이 없습니다.");
        }
        return responseList;
    }

    //해당 날짜 출석부 생성
    @Transactional
    public void createAttendanceInfo(Long meetingId, LocalDate date, String email) {
        User user = userRepository.findByEmail(email).orElse(null);//주최자
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        
        //주최자나 모임이 없는경우
        if (user == null || meeting == null) {
            throw new IllegalArgumentException("주최자 또는 모임이 없습니다.");
        }
        //주최자랑 요청자랑 같은지 확인
        if(user.getId()!=meeting.getUser().getId()){
            throw new IllegalArgumentException("주최자가 아닙니다.");
        
        }

        //참여자목록 불러오기
        List<Participant> participantList = participantRepository.findByMeetingId(meetingId);
        if (participantList == null || participantList.isEmpty()) {
            throw new IllegalStateException("참여자가 없습니다.");
        }

        // 출석 정보 생성
    for (Participant participant : participantList) {
        // 해당 날짜의 출석 정보가 이미 있는지 확인
        Attend existingAttend = attendRepository.findByParticipantAndDate(participant, date).orElse(null);
        if (existingAttend != null) {
            // 이미 출석 정보가 있는 경우 롤백
            throw new IllegalStateException("이미 출석정보가 존재합니다.");
        }

        // 출석 정보 생성
        Attend attend = Attend.builder()
                        .participant(participant)// 참여자 설정
                        .date(date) // 출석 일자 설정
                        .attendStatus(AttendStatus.ABSENCE)// 출석 상태 기본으로 대기 상태로 설정
                        .build();
        
        // 출석 정보 저장
        attendRepository.save(attend);
        }
    }

    
    
}
