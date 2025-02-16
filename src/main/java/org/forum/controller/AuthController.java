package org.forum.controller;

import org.forum.model.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/register")
    public String register(Model model) {
        // todo: register
        model.addAttribute("userDto", new UserDto());
        return "register";
    }
    @GetMapping("/login")
    public String login() {
        // todo: do auth
        return "login";
    }
    @GetMapping("/logout")
    public String logout() {
        // todo: clear session
        return "login";
    }
}
