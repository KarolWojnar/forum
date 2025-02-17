package org.forum.service;

import lombok.RequiredArgsConstructor;
import org.forum.model.dto.UserDto;
import org.forum.model.entity.User;
import org.forum.model.mapper.UserMapper;
import org.forum.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public String createUser(UserDto userDto, Model model) {
        if (!validateUser(userDto, model)) {
            return "register";
        }
        saveUser(userDto, model);
        return "login";
    }

    private void saveUser(UserDto userDto, Model model) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userRepository.save(UserMapper.toEntity(userDto));
        emailService.sendEmail(userDto.getEmail(), user.getActivationToken());
        model.addAttribute("success", "User created successfully.");
        model.addAttribute("username", userDto.getUsername());
    }

    private boolean validateUser(UserDto userDto, Model model) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already exists.");
            return false;
        } else if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists.");
            return false;
        } else if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match.");
            return false;
        } else if (userDto.getPassword().length() < 8) {
            model.addAttribute("error", "Password must be at least 8 characters.");
            return false;
        } else if (userDto.getUsername().length() < 3) {
            model.addAttribute("error", "Username must be at least 3 characters.");
            return false;
        }
        return true;
    }

    @Scheduled(fixedRate = 1000)
    public void removeInactiveAccounts() {
        List<User> users = userRepository.findAllByActivatedFalseAndCreateDateBefore(LocalDateTime.now());
        log.info("Removing {} inactive accounts.", users.size());
        userRepository.deleteAll(users);
    }
}
