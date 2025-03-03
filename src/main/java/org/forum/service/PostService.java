package org.forum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.forum.exception.PostException;
import org.forum.model.dto.PostDto;
import org.forum.model.dto.PostInfoDto;
import org.forum.model.entity.User;
import org.forum.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        if (!ValidationUtil.validPost(post, redAttrs)) {
            return "redirect:/posts/new";
        }
        User user = userService.findAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication());
        postRepository.save(PostDto.toEntity(post, user));
        redAttrs.addFlashAttribute("success", "Post created successfully.");
        return "redirect:/posts";
    }

    public Page<PostInfoDto> getPosts(int page, String keyWord, String sort, String direction) {
        if (sort != null && direction != null) {
            Sort sorted = Sort.by(Sort.Direction.fromString(direction), sort);
            Pageable pageable = PageRequest.of(page, 10, sorted);
            return postRepository.findPostsInfo(pageable, keyWord);
        }
        Pageable pageable = Pageable.ofSize(10).withPage(page);
        return postRepository.findPostsInfo(pageable, keyWord);
    }

    public String archivePost(Long id) {
        User user = userService.findAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication());
        if (user.getRole().equals("ADMIN")) {
            postRepository.archivePost(id);
        } else {
            int updatedRecords = postRepository.archivePostByIdAndUser(user, id);
            if (updatedRecords == 0) {
                throw new PostException("You are not authorized to archive this post.", "/posts");
            }
        }
        return "posts";
    }
}
