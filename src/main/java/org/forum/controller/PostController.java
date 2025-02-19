package org.forum.controller;

import lombok.RequiredArgsConstructor;
import org.forum.model.dto.PostDto;
import org.forum.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping()
    public String posts() {
        return "posts";
    }

    @GetMapping("/new")
    public String newPost() {
        return "new-post";
    }

    @PostMapping("/new")
    public String createPost(@ModelAttribute PostDto post, RedirectAttributes redirect) {
        return postService.createPost(post, redirect);
    }
}
