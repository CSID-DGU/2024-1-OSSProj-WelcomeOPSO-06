package com.backend.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.entity.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long>{

    // 모임 ID와 참여자 아이디로 조회하는 메서드
    Optional<Participant> findByMeetingIdAndUserId(Long meetingId, Long userId);
    List<Participant> findByMeetingId(Long meetingId);//모임의 모든 참여자목록
    List<Participant> findByUserId(Long userId);//참여자의 모든 모임참여목록
}