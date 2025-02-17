package org.forum.controller;

import lombok.RequiredArgsConstructor;
import org.forum.model.dto.UserDto;
import org.forum.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto, Model model) {
        return userService.createUser(userDto, model);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
