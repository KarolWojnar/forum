package org.forum.model.dto;

import org.forum.model.entity.Comment;

import java.time.LocalDateTime;

public record CommentDto (
        Long id,
        String content,
        String author,
        boolean isActivePost,
        LocalDateTime createDate,
        boolean hasReplies
){
    public static CommentDto fromEntity(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getUser() == null ? "Anonymous" : comment.getUser().getUsername(),
                comment.getPost().isActive(),
                comment.getCreateDate(),
                !comment.getReplies().isEmpty()
        );
    }
}
