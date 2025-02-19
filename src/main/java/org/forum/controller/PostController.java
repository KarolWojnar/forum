package org.forum.controller;

import lombok.RequiredArgsConstructor;
import org.forum.model.dto.PostDto;
import org.forum.model.dto.PostInfoDto;
import org.forum.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping()
    public String posts(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Page<PostInfoDto> posts = postService.getPosts(page);
        model.addAttribute("posts", posts);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        return "posts";
    }

    @GetMapping("/{id}")
    public String loadCommentsForPost(@PathVariable("id") Long id, Model model) {
        model.addAttribute("details", postService.getPostComments(id));
        return "fragments/post :: details";
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
