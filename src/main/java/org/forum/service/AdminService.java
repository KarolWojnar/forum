package org.forum.service;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.forum.model.ActivationType;
import org.forum.model.dto.UserDto;
import org.forum.model.entity.Activation;
import org.forum.model.entity.User;
import org.forum.repository.ActivationRepository;
import org.forum.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
@RequiredArgsConstructor
public class AdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);
    private final EmailService emailService;
    private final ActivationRepository activationRepository;
    private final UserRepository userRepository;

    public String sendInvite(String email, RedirectAttributes redirect) {
        if (email != null && !email.isEmpty()) {
            try {
                InternetAddress internetAddress = new InternetAddress(email);
                internetAddress.validate();
                log.info(internetAddress.getAddress());
                Activation adminActive = new Activation(ActivationType.ADMIN_INVITE);
                activationRepository.save(adminActive);
                emailService.sendEmailAdminInvitation(email, adminActive.getActivationCode(), adminActive.getExpiresAt());
                redirect.addFlashAttribute("success", "Invite sent to " + email);
            } catch (AddressException e) {
                redirect.addFlashAttribute("error", "Please enter a valid email address");
                redirect.addFlashAttribute("email", email);
            }
        } else {
            redirect.addFlashAttribute("error", "Please enter a valid email address");
        }
        return "redirect:/admin";
    }

    public void getAllUsers(int page, String username, Model model) {
        Pageable pageable = PageRequest.of(page , 10);
        Page<User> users = userRepository.findAllByUsernameContainingOrEmailContaining(username, username, pageable);
        model.addAttribute("users", users.map(UserDto::mapToUserDto));
        model.addAttribute("currentPage", page);
        model.addAttribute("username", username);
        model.addAttribute("totalPages", users.getTotalPages());
    }
}
