package com.backend.backend.repository;

import com.backend.backend.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByModifiedAtDesc();
    //Optional<Board> findById(Long id);
    Optional<Board> findByMeetingId(Long meetingId);
    //void deleteAllByUser(User user);
}
