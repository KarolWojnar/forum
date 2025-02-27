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
    public String getUser(@PathVariable("username") String username, RedirectAttributes redirect, Model model) {
        return userService.getUserData(username, redirect, model);
    }

    @PostMapping
    public String changeUsername(@ModelAttribute UserDto user, RedirectAttributes redirect, HttpSession session) {
        return userService.updateUsername(user, redirect, session);
    }
}
