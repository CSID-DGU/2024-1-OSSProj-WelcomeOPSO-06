package com.backend.backend.repository;

import com.backend.backend.entity.Comment;
import com.backend.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUser(Long id, User user);

    void deleteAllByUser(User user);
}
