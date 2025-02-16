package org.forum.controller;

import lombok.RequiredArgsConstructor;
import org.forum.model.dto.UserDto;
import org.forum.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping()
    public String register(@ModelAttribute UserDto userDto, Model model) {
        return userService.createUser(userDto, model);
    }


}
