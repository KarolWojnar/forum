package org.forum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.forum.exception.UserException;
import org.forum.model.dto.CommentDto;
import org.forum.model.dto.PostDto;
import org.forum.model.dto.PostInfoDto;
import org.forum.model.entity.Comment;
import org.forum.model.entity.User;
import org.forum.repository.CommentRepository;
import org.forum.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    public String createPost(PostDto post, RedirectAttributes redAttrs) {
        if (!validPost(post, redAttrs)) {
            return "redirect:/posts/new";
        }
        try {
            User user = userService.findAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication(), redAttrs);
            postRepository.save(PostDto.toEntity(post, user));
            return "redirect:/posts";
        } catch (UserException e) {
            return "redirect:/login";
        }
    }

    private boolean validPost(PostDto post, RedirectAttributes redAttrs) {
        if (post.getTitle().isEmpty()) {
            redAttrs.addFlashAttribute("error", "Title cannot be empty.");
            return false;
        } else if (post.getContent().isEmpty()) {
            redAttrs.addFlashAttribute("error", "Content cannot be empty.");
            return false;
        } else if (post.getTitle().length() > 100) {
            redAttrs.addFlashAttribute("error", "Title cannot be longer than 100 characters.");
            return false;
        } else if (post.getContent().length() > 1000) {
            redAttrs.addFlashAttribute("error", "Content cannot be longer than 1000 characters.");
            return false;
        } else {
            return true;
        }
    }

    public Page<PostInfoDto> getPosts(int page) {
        Pageable pageable = Pageable.ofSize(10).withPage(page);
        return postRepository.findPostsInfo(pageable);
    }

    public List<CommentDto> getPostComments(Long id) {
        List<Comment> comments = commentRepository.findAllByPost_IdAndParentCommentNullOrderByCreateDate(id);
        return comments.stream().map(CommentDto::fromEntity).toList();
    }
}
