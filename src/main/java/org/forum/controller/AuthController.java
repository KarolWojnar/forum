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
        userService.forgotPassword(email, redirectAttributes);
        return "redirect:/login";
    }

    @GetMapping("/reset-password/{uid}")
    public String resetPassword(@PathVariable("uid") String uid, Model model) {
        userService.resetPassword(uid, model);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute ResetPasswordDto resetPasswordDto, RedirectAttributes redirectAttributes) {
        if (!userService.changePassword(resetPasswordDto, redirectAttributes)) {
            return "redirect:/reset-password/" + resetPasswordDto.getToken();
        }
        return "redirect:/login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto, @RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        userService.createUser(userDto, redirectAttributes, token);
        return "redirect:/login";
    }

    @GetMapping("/activate/{uid}")
    public String activate(@PathVariable("uid") String uid, Model model) {
        userService.activateUser(uid, model);
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
