package org.forum.controller;


import lombok.RequiredArgsConstructor;
import org.forum.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable("id") Long id, RedirectAttributes redirect) {
        commentService.deleteComment(id, redirect);
        return "posts";
    }
}
