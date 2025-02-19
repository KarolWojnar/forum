package org.forum.model.dto;

import lombok.Data;
import org.forum.model.entity.Post;
import org.forum.model.entity.User;

@Data
public class PostDto {
    private String title;
    private String content;

    public static Post toEntity(PostDto postDto, User user) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setAuthor(user);
        return post;
    }
}
