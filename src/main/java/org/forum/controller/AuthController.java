package org.forum.controller;

import lombok.RequiredArgsConstructor;
import org.forum.model.dto.UserDto;
import org.forum.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String register(@ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes) {
        return userService.createUser(userDto, redirectAttributes);
    }

    @GetMapping("/activate/{uid}")
    public String activate(@PathVariable("uid") String uid, Model model) {
        return userService.activateUser(uid, model);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
