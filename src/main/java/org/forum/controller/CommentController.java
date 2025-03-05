package org.forum.controller;


import lombok.RequiredArgsConstructor;
import org.forum.model.dto.CommentDto;
import org.forum.model.dto.NewCommentDto;
import org.forum.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return "posts";
    }

    @GetMapping("/{id}/replies")
    public String getReplies(@PathVariable("id") Long id, Model redirect) {
        var comment = commentService.getReplies(id);
        if (comment.isEmpty()) {
            return "redirect:/posts";
        }
        redirect.addAttribute("details", comment.stream().map(CommentDto::fromEntity).toList());
        return "fragments/post :: list-comment";
    }

    @PostMapping("/{id}/replies")
    public String replyComment(@PathVariable("id") Long id, @ModelAttribute NewCommentDto comment, RedirectAttributes redirect) {
        long postId = commentService.addComment(id, comment, redirect);
        return "redirect:/posts/" + postId;
    }
}
