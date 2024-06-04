package com.backend.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend.entity.Board;
import com.backend.backend.entity.Comment;
import com.backend.backend.entity.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUser(Long id, User user);

    void deleteAllByUser(User user);

    //댓글목록 불러오기
    List<Comment> findByBoard(Board board);
}
