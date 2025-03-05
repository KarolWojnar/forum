package org.forum.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.forum.model.dto.UserDto;
import org.forum.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public String getUser(@PathVariable("username") String username, Model model) {
        userService.getUserData(username, model);
        return "profile";
    }

    @PostMapping
    public String changeUsername(@ModelAttribute UserDto user, RedirectAttributes redirect, HttpSession session) {
        userService.updateUsername(user, redirect, session);
        return "redirect:/login";
    }
}
