package org.forum.service;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.forum.model.dto.NewCommentDto;
import org.forum.model.dto.PostDto;
import org.forum.model.dto.UserDto;
import org.forum.model.entity.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ValidationUtil {
    protected static boolean validComment(NewCommentDto comment, RedirectAttributes redirect) {
        if (comment == null || comment.getContent().isEmpty()) {
            redirect.addFlashAttribute("error", "Comment cannot be empty.");
            return false;
        } else if (comment.getContent().length() > 500) {
            redirect.addFlashAttribute("error", "Comment cannot be longer than 500 characters.");
            return false;
        }
        return true;
    }

    protected static boolean validPost(PostDto post, RedirectAttributes redAttrs) {
        if (post.getTitle().isEmpty()) {
            redAttrs.addFlashAttribute("error", "Title cannot be empty.");
            return false;
        } else if (post.getContent().isEmpty()) {
            redAttrs.addFlashAttribute("error", "Content cannot be empty.");
            return false;
        } else if (post.getTitle().length() > 100) {
            redAttrs.addFlashAttribute("error", "Title cannot be longer than 100 characters.");
            return false;
        } else if (post.getContent().length() > 1000) {
            redAttrs.addFlashAttribute("error", "Content cannot be longer than 1000 characters.");
            return false;
        }
        return true;
    }

    protected static boolean validateUser(UserDto userDto, User user, RedirectAttributes redirectAttributes) {
        if (user != null) {
            redirectAttributes.addFlashAttribute("error", "User already exist.");
            return false;
        } else if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return false;
        } else if (userDto.getPassword().length() < 8) {
            redirectAttributes.addFlashAttribute("error", "Password must be at least 8 characters.");
            return false;
        } else if (userDto.getUsername().length() < 3) {
            redirectAttributes.addFlashAttribute("error", "Username must be at least 3 characters.");
            return false;
        }
        try {
            InternetAddress internetAddress = new InternetAddress(userDto.getEmail());
            internetAddress.validate();
        } catch (AddressException e) {
            redirectAttributes.addFlashAttribute("error", "Please enter a valid email address.");
            return false;
        }
        return true;
    }

}
