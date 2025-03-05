package org.forum.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.forum.exception.MailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.url}")
    private String url;

    private final JavaMailSender javaMailSender;

    public void sendEmailActivationUser(String to, String activationToken) {
        try {
            String urlLink = url + "activate/" + activationToken;
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(ConstantsString.ACTIVATION_EMAIL_SUBJECT);
            helper.setText(ConstantsString.ACTIVATION_EMAIL_TEMPLATE.formatted(urlLink, urlLink, urlLink), true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MailException("Error sending email", e, "register");
        }
    }

    public void sendEmailAdminInvitation(String email, String activationCode, LocalDateTime expiresAt) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject(ConstantsString.ADMIN_INVITATION_SUBJECT);

            String urlLink = url + "register?token=" + activationCode;
            String dateFormat = expiresAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            helper.setText(ConstantsString.ADMIN_INVITATION_TEMPLATE.formatted(urlLink, urlLink, urlLink, dateFormat), true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new MailException("Error sending admin invitation email", e, "/admin");
        }
    }

    public void sendEmailResetPassword(String to, String activationToken) {
        try {
            String urlLink = url + "reset-password/" + activationToken;
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(ConstantsString.RESET_PASSWORD_SUBJECT);
            helper.setText(ConstantsString.RESET_PASSWORD_TEMPLATE.formatted(urlLink, urlLink, urlLink), true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MailException("Error sending email", e, "/forgot-password");
        }
    }
}
