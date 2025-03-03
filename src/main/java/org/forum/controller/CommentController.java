package org.forum.controller;


import lombok.RequiredArgsConstructor;
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
        return commentService.deleteComment(id);
    }

    @GetMapping("/{id}/replies")
    public String getReplies(@PathVariable("id") Long id, Model redirect) {
        return commentService.getReplies(id, redirect);
    }

    @PostMapping("/{id}/replies")
    public String replyComment(@PathVariable("id") Long id, @ModelAttribute NewCommentDto comment, RedirectAttributes redirect) {
        return commentService.addComment(id, comment, redirect);
    }
}
