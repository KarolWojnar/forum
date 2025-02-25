package org.forum.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.forum.model.entity.Post;
import org.forum.model.entity.User;

@AllArgsConstructor
@Data
@NoArgsConstructor
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
