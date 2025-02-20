package org.forum.repository;

import org.forum.model.entity.Comment;
import org.forum.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost_IdAndParentCommentNullOrderByCreateDate(Long postId);
    Optional<Comment> findByIdAndUser(Long id, User user);
}
