package org.forum.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.forum.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping()
    public String admin(@RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "username", defaultValue = "") String username,
                        Model model,
                        HttpServletRequest request) {
        adminService.getAllUsers(page, username, model);
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return "admin :: user-list";
        }
        return "admin";
    }

    @PostMapping("/invite")
    public String inviteAdmin(@RequestParam("email") String email, RedirectAttributes redirect) {
        return adminService.sendInvite(email, redirect);
    }
}
