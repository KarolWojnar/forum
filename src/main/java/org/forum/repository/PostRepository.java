package org.forum.repository;

import org.forum.model.dto.PostInfoDto;
import org.forum.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
    SELECT new org.forum.model.dto.PostInfoDto(p.id, p.title, p.content, p.createDate, u.username, COUNT(c.id))\s
    FROM Post p
    LEFT JOIN p.author u
    LEFT JOIN Comment c ON c.post.id = p.id
    GROUP BY p.id
    ORDER BY COUNT(c.id) DESC
   \s""")
    Page<PostInfoDto> findPostsInfo(Pageable pageable);
}
