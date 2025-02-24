package org.forum.repository;

import org.forum.model.dto.PostInfoDto;
import org.forum.model.entity.Post;
import org.forum.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
    SELECT new org.forum.model.dto.PostInfoDto(p.id, p.title, p.content, p.isActive, p.createDate as date, u.username, COUNT(c.id) AS comments)\s
    FROM Post p
    LEFT JOIN p.author u
    LEFT JOIN Comment c ON c.post.id = p.id
    WHERE p.title LIKE %?1%
    GROUP BY p.id, p.title, p.content, p.isActive, p.createDate, u.username
   \s""")
    Page<PostInfoDto> findPostsInfo(Pageable pageable, String keyWord);

    @Transactional
    @Modifying
    @Query("""
    UPDATE Post p
    SET p.isActive = false
    WHERE p.id = ?1
   \s""")
    void archivePost(Long id);

    @Transactional
    @Modifying
    @Query("""
    UPDATE Post p
    SET p.isActive = false
    WHERE p.id = ?2
    AND p.author = ?1
   \s""")
    int archivePostByIdAndUser(User user, Long id);
}
