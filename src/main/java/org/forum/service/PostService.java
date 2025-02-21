package org.forum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.forum.exception.UserException;
import org.forum.model.dto.PostDto;
import org.forum.model.dto.PostInfoDto;
import org.forum.model.entity.User;
import org.forum.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

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

    public Page<PostInfoDto> getPosts(int page, String keyWord) {
        Pageable pageable = Pageable.ofSize(10).withPage(page);
        return postRepository.findPostsInfo(pageable, keyWord);
    }

    public void archivePost(Long id, RedirectAttributes redirect) {
        try {
            User user = userService.findAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication(), redirect);
            if (user.getRole().equals("ADMIN")) {
                postRepository.archivePost(id);
            } else {
                int updatedRecords = postRepository.archivePostByIdAndUser(user, id);
                if (updatedRecords == 0) {
                    redirect.addFlashAttribute("error", "You are not the author of this post.");
                }
            }
        } catch (UserException e) {
            throw new UserException("User not found or unauthorized.");
        }
    }
}
