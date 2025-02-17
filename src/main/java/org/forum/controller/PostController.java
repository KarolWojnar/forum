package org.forum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    @GetMapping()
    public String posts() {
        return "posts";
    }

    @GetMapping("/new")
    public String newPost() {
        return "new-post";
    }

}
