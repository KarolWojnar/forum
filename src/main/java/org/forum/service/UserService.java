package org.forum.service;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.forum.exception.MailException;
import org.forum.exception.UserException;
import org.forum.model.ActivationType;
import org.forum.model.dto.ResetPasswordDto;
import org.forum.model.dto.UserDto;
import org.forum.model.entity.Activation;
import org.forum.model.entity.User;
import org.forum.model.mapper.UserMapper;
import org.forum.repository.ActivationRepository;
import org.forum.repository.UserRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public String createUser(UserDto userDto, RedirectAttributes redirectAttributes, String token) {
        User user = userRepository.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail()).orElse(null);
        if (!ValidationUtil.validateUser(userDto, user, redirectAttributes)) {
            redirectAttributes.addFlashAttribute("username", userDto.getUsername());
            redirectAttributes.addFlashAttribute("email", userDto.getEmail());
            if (token != null) {
                return "redirect:/register?token=" + token;
            }
            return "redirect:/register";
        }
        try {
            return saveUser(userDto, checkToken(token), redirectAttributes);
        } catch (UserException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("username", userDto.getUsername());
            redirectAttributes.addFlashAttribute("email", userDto.getEmail());
            return "redirect:/register";
        }
    }

    private boolean checkToken(String token) {
        if (token != null && !token.isBlank()) {
            Optional<Activation> activation = activationRepository.findByActivationCodeAndType(token, ActivationType.ADMIN_INVITE);
            if (activation.isEmpty()) {
                throw new UserException("Invalid activation link.");
            }
            if (activation.get().getExpiresAt().isBefore(LocalDateTime.now())) {
                activationRepository.delete(activation.get());
                throw new UserException("Activation link expired.");
            }
            activationRepository.delete(activation.get());
            return true;
        }
        return false;
    }

    public String saveUser(UserDto userDto, boolean haveAdminToken, RedirectAttributes redirectAttributes) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Activation active = new Activation(ActivationType.REGISTRATION);
        User user = UserMapper.toEntity(userDto);
        if (haveAdminToken) {
            user.setRole("ADMIN");
        }
        try {
            active.setUser(userRepository.save(user));
            String token = activationRepository.save(active).getActivationCode();
            emailService.sendEmailActivationUser(userDto.getEmail(), token);
            redirectAttributes.addFlashAttribute("success", "User created successfully. Active your account on email.");
            redirectAttributes.addFlashAttribute("username", userDto.getUsername());
            log.info("User {} created.", user.getUsername());
            return "redirect:/login";
        } catch (MailException e) {
            activationRepository.delete(active);
            userRepository.delete(user);
            redirectAttributes.addFlashAttribute("username", userDto.getUsername());
            redirectAttributes.addFlashAttribute("email", userDto.getEmail());
            redirectAttributes.addFlashAttribute("error", "Error sending email.");
            return "redirect:/register";
        }
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
        Optional<Activation> activation = activationRepository.findByActivationCodeAndType(uid, ActivationType.REGISTRATION);
        if (activation.isEmpty()) {
            model.addAttribute("error", "Invalid activation link.");
        } else {
            if (activation.get().getExpiresAt().isBefore(LocalDateTime.now())) {
                activationRepository.delete(activation.get());
                model.addAttribute("error", "Activation link has expired.");
                return "login";
            }
            User user = activation.get().getUser();
            user.setActivated(true);
            userRepository.save(user);
            activationRepository.delete(activation.get());
            log.info("User {} activated.", user.getUsername());
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

    public String forgotPassword(String email, RedirectAttributes redirectAttributes) {
        if (email == null || email.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Email is required.");
            return "redirect:/forgot-password";
        }

        try {
            InternetAddress emailValid = new InternetAddress(email);
            emailValid.validate();
        } catch (AddressException e) {
            redirectAttributes.addFlashAttribute("error", "Email is not valid.");
            return "redirect:/forgot-password";
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/forgot-password";
        }
        Activation activation = new Activation(ActivationType.PASSWORD_RESET);
        activation.setUser(user);
        String token = activationRepository.save(activation).getActivationCode();
        try {
            emailService.sendEmailResetPassword(email, token);
        } catch (MailException e) {
            activationRepository.delete(activation);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/forgot-password";
        }
        redirectAttributes.addFlashAttribute("success", "Password reset link sent to your email.");
        return "redirect:/login";
    }

    public String resetPassword(String uid, Model model) {
        Activation activation = activationRepository.findByActivationCodeAndType(uid, ActivationType.PASSWORD_RESET).orElse(null);
        if (activation == null || activation.getExpiresAt().isBefore(LocalDateTime.now())) {
            model.addAttribute("invalidToken", "Invalid reset password link or token expired.");
            return "reset-password";
        }
        model.addAttribute("token", uid);
        model.addAttribute("username", activation.getUser().getUsername());
        return "reset-password";
    }

    public String changePassword(ResetPasswordDto resetPasswordDto, RedirectAttributes redirectAttributes) {
        if (!ValidationUtil.validPassword(resetPasswordDto.getPassword(), resetPasswordDto.getConfirmPassword(), redirectAttributes)) {
            redirectAttributes.addFlashAttribute("token", resetPasswordDto.getToken());
            redirectAttributes.addFlashAttribute("username", resetPasswordDto.getUsername());
            return "redirect:/reset-password/" + resetPasswordDto.getToken();
        }
        Activation activation = activationRepository.findByActivationCodeAndType(resetPasswordDto.getToken(), ActivationType.PASSWORD_RESET).orElse(null);
        if (activation == null || activation.getExpiresAt().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("invalidToken", "Invalid reset password link or token expired.");
            return "redirect:/reset-password" + resetPasswordDto.getToken();
        }
        User user = activation.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
        userRepository.save(user);
        activationRepository.delete(activation);
        redirectAttributes.addFlashAttribute("success", "Password changed successfully.");
        return "redirect:/login";
    }

    public String getUserData(String username, RedirectAttributes redirect, Model model) {
        User userAuth = findAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication(), redirect);
        User user = userRepository.findByUsername(username).orElse(null);
        if (!userAuth.equals(user)) {
            return "redirect:/users/" + userAuth.getUsername();
        }
        model.addAttribute("user", UserDto.mapToUserDto(user));
        return "profile";
    }

    public String updateUsername(UserDto user, RedirectAttributes redirect, HttpSession session) {
        User userAuth = findAuthenticatedUser(SecurityContextHolder.getContext().getAuthentication(), redirect);
        String oldUsername = userAuth.getUsername();
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            redirect.addFlashAttribute("error", "Username is required.");
            return "redirect:/users/" + oldUsername;
        }

        if (user.getUsername().equals(oldUsername)) {
            redirect.addFlashAttribute("error", "Username is the same.");
            return "redirect:/users/" + oldUsername;
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            redirect.addFlashAttribute("error", "Username already exists.");
            return "redirect:/users/" + oldUsername;
        }

        userAuth.setUsername(user.getUsername());
        userRepository.save(userAuth);

        SecurityContextHolder.clearContext();
        session.invalidate();
        redirect.addFlashAttribute("success", "Username changed successfully.");
        redirect.addFlashAttribute("username", user.getUsername());
        log.info("User {} changed username to {}.", oldUsername, user.getUsername());
        return "redirect:/login";
    }
}
