package org.forum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.forum.exception.UserException;
import org.forum.model.dto.UserDto;
import org.forum.model.entity.Activation;
import org.forum.model.entity.User;
import org.forum.model.mapper.UserMapper;
import org.forum.repository.ActivationRepository;
import org.forum.repository.UserRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ActivationRepository activationRepository;

    public String createUser(UserDto userDto, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail()).orElse(null);
        if (!ValidationUtil.validateUser(userDto, user, redirectAttributes)) {
            return "redirect:/register";
        }
        saveUser(userDto, redirectAttributes);
        return "redirect:/login";
    }

    public void saveUser(UserDto userDto, RedirectAttributes redirectAttributes) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Activation active = new Activation();
        active.setUser(userRepository.save(UserMapper.toEntity(userDto)));
        String token = activationRepository.save(active).getActivationCode();
        emailService.sendEmail(userDto.getEmail(), token);
        redirectAttributes.addFlashAttribute("success", "User created successfully.");
        redirectAttributes.addFlashAttribute("username", userDto.getUsername());
    }

    @Scheduled(cron = "0 0 * * * *")
    public void removeInactiveAccounts() {
        List<User> users = userRepository.findAllByActivatedFalseAndCreateDateBefore(LocalDateTime.now().minusDays(1));
        List<Activation> activations = activationRepository.findAllByExpiresAtBefore(LocalDateTime.now());
        log.info("Removing {} inactive accounts.", users.size());
        activationRepository.deleteAll(activations);
        userRepository.deleteAll(users);
    }

    public String activateUser(String uid, Model model) {
        Optional<Activation> activation = activationRepository.findByActivationCode(uid);
        if (activation.isEmpty()) {
            model.addAttribute("error", "Invalid activation link.");
        } else {
            User user = activation.get().getUser();
            user.setActivated(true);
            userRepository.save(user);
            activationRepository.delete(activation.get());
            model.addAttribute("success", "Account activated successfully.");
            model.addAttribute("username", user.getUsername());
        }
        return "login";
    }

    public User findAuthenticatedUser(Authentication auth, RedirectAttributes redAttrs) {
        if (auth == null) {
            redAttrs.addFlashAttribute("error", "Failed authentication.");
            throw new UserException("Failed authentication");
        }

        Object principal = auth.getPrincipal();
        if ((!(principal instanceof UserDetails userDetails))) {
            redAttrs.addFlashAttribute("error", "Failed authentication.");
            throw new UserException("Failed authentication");
        }

        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);

        if (user == null) {
            redAttrs.addFlashAttribute("error", "Failed authentication.");
            throw new UserException("Failed authentication");
        }
        return user;
    }
}
