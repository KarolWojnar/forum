package org.forum.repository;

import org.forum.model.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"user", "post", "replies"})
    List<Comment> findAllByPost_IdAndParentCommentNullOrderByCreateDate(Long postId);
    @EntityGraph(attributePaths = {"user", "post", "replies"})
    List<Comment> findAllByParentComment_Id(Long postId);
}
