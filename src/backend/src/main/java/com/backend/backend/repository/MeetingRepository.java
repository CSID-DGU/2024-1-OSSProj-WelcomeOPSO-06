package com.backend.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.entity.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
