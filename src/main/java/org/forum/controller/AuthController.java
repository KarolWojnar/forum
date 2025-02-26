package org.forum.controller;

import lombok.RequiredArgsConstructor;
import org.forum.model.dto.ResetPasswordDto;
import org.forum.model.dto.UserDto;
import org.forum.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("token", null);
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        return userService.forgotPassword(email, redirectAttributes);
    }

    @GetMapping("/reset-password/{uid}")
    public String resetPassword(@PathVariable("uid") String uid, Model model) {
        return userService.resetPassword(uid, model);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute ResetPasswordDto resetPasswordDto, RedirectAttributes redirectAttributes) {
        return userService.changePassword(resetPasswordDto, redirectAttributes);
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto, @RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        return userService.createUser(userDto, redirectAttributes, token);
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
