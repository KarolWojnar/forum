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
            helper.setSubject("Activate your account");
            String htmlContent = """
                <html>
                <body>
                    <h2 style="color: #007bff;">Welcome to our forum!</h2>
                    <p>Click the button below to activate your account:</p>
                    <p><a href='%s' style='display: inline-block; padding: 10px 20px; background-color: #28a745;
                     color: white; text-decoration: none; border-radius: 5px;'>Activate Account</a></p>
                    <p>If the button doesn't work, use the following link:</p>
                    <p><a href='%s'>%s</a></p>
                    <p>Best regards,<br>Forum Team</p>
                </body>
                </html>
            """.formatted(urlLink, urlLink, urlLink);
            helper.setText(htmlContent, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MailException("Error sending email", e);
        }
    }

    public void sendEmailAdminInvitation(String email, String activationCode, LocalDateTime expiresAt) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject("Admin Invitation");

            String urlLink = url + "register?token=" + activationCode;
            String dateFormat = expiresAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            String htmlContent = """
                <html>
                <body>
                    <h2 style="color: #007bff;">You have been invited to become an admin!</h2>
                    <p>Click the button below to complete your admin registration:</p>
                    <p><a href='%s' style='display: inline-block; padding: 10px 20px; background-color: #28a745;
                     color: white; text-decoration: none; border-radius: 5px;'>Register as Admin</a></p>
                    <p>If the button doesn't work, use the following link:</p>
                    <p><a href='%s'>%s</a></p>
                    <p>This invitation will expire on: %s</p>
                    <p>Best regards,<br>Forum Team</p>
                </body>
                </html>
            """.formatted(urlLink, urlLink, urlLink, dateFormat);

            helper.setText(htmlContent, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new MailException("Error sending admin invitation email", e);
        }
    }

    public void sendEmailResetPassword(String to, String activationToken) {
        try {
            String urlLink = url + "reset-password/" + activationToken;
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject("Reset your password");
            String htmlContent = """
                <html>
                <body>
                    <h2 style="color: #007bff;">Reset your password</h2>
                    <p>Click the button below to reset your password:</p>
                    <p><a href='%s' style='display: inline-block; padding: 10px 20px; background-color: #28a745;
                     color: white; text-decoration: none; border-radius: 5px;'>Reset Password</a></p>
                    <p>If the button doesn't work, use the following link:</p>
                    <p><a href='%s'>%s</a></p>
                    <p>Best regards,<br>Forum Team</p>
                </body>
                </html>
            """.formatted(urlLink, urlLink, urlLink);
            helper.setText(htmlContent, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MailException("Error sending email", e);
        }
    }
}
