package org.forum.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.forum.model.dto.NewCommentDto;
import org.forum.model.dto.PostDto;
import org.forum.model.dto.PostInfoDto;
import org.forum.service.CommentService;
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
    private final CommentService commentService;

    @GetMapping()
    public String posts(@RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "key-word", defaultValue = "") String keyWord,
                        @RequestParam(name = "sort", defaultValue = "comments") String sort,
                        @RequestParam(name = "direction", defaultValue = "desc") String direction,
                        Model model,
                        HttpServletRequest request) {
        Page<PostInfoDto> posts = postService.getPosts(page, keyWord, sort, direction);
        model.addAttribute("posts", posts);
        model.addAttribute("page", page);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("totalPages", posts.getTotalPages());

        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return "posts :: postFragment";
        }

        return "posts";
    }

    @GetMapping("/{id}")
    public String loadCommentsForPost(@PathVariable("id") Long id, Model model) {
        model.addAttribute("details", commentService.getPostComments(id));
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

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable("id") Long postId, @ModelAttribute NewCommentDto comment, RedirectAttributes redirect) {
        return commentService.addComment(postId, comment, redirect);
    }

    @PatchMapping("/{id}/archive")
    public String archivePost(@PathVariable("id") Long id) {
        return postService.archivePost(id);
    }
}
