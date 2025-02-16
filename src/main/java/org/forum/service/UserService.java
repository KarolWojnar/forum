package org.forum.service;

import lombok.RequiredArgsConstructor;
import org.forum.model.dto.UserDto;
import org.forum.model.mapper.UserMapper;
import org.forum.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String createUser(UserDto userDto, Model model) {
        if (!validateUser(userDto, model)) {
            return "home";
        }
        saveUser(userDto, model);
        return "redirect:/users/register";
    }

    private void saveUser(UserDto userDto, Model model) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(UserMapper.toEntity(userDto));
        model.addAttribute("success", "User created successfully");
        model.addAttribute("email", userDto.getEmail());
    }

    private boolean validateUser(UserDto userDto, Model model) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already exists");
            return false;
        } else if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return false;
        } else if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return false;
        } else if (userDto.getPassword().length() < 8) {
            model.addAttribute("error", "Password must be at least 8 characters");
            return false;
        } else if (userDto.getUsername().length() < 3) {
            model.addAttribute("error", "Username must be at least 3 characters");
            return false;
        }
        return true;
        
    }
}
